package com.saitama.microservices.authenticationservice.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.saitama.microservices.authenticationservice.dto.AccessRequestDTO;
import com.saitama.microservices.authenticationservice.dto.JwtDTO;
import com.saitama.microservices.authenticationservice.dto.LogoutDTO;
import com.saitama.microservices.authenticationservice.dto.TempUserDTO;
import com.saitama.microservices.authenticationservice.dto.TemporaryAccessGrantDTO;
import com.saitama.microservices.authenticationservice.entity.TempUser;
import com.saitama.microservices.authenticationservice.exception.UserExistsException;
import com.saitama.microservices.authenticationservice.exception.UserLockedException;
import com.saitama.microservices.authenticationservice.exception.UserNotVerifiedException;
import com.saitama.microservices.authenticationservice.mapper.TempUserMapper;
import com.saitama.microservices.authenticationservice.repository.TempUserRepository;
import com.saitama.microservices.authenticationservice.service.IKeycloakService;
import com.saitama.microservices.authenticationservice.service.ITempUserService;
import com.saitama.microservices.authenticationservice.task.EmailVerificationTask;
import com.saitama.microservices.commonlib.exception.CommonInternalException;
import com.saitama.microservices.commonlib.exception.EntityNotFoundException;
import com.saitama.microservices.commonlib.util.UuidUtils;


@Service
public class TempUserServiceImpl implements ITempUserService {
	
	private static final Logger LOG = LoggerFactory.getLogger(TempUserServiceImpl.class);
	
	private final IKeycloakService keycloakService;
	private final TempUserRepository tempUserRepository;
	private final TempUserMapper tempUserMapper;
	private final EmailVerificationTask emailVerificationTask;

	@Value("${encryption.salt}")
	private String encryptionSalt;
	
	@Autowired
	public TempUserServiceImpl(
			IKeycloakService keycloakService,
			TempUserRepository tempUserRepository,
			TempUserMapper tempUserMapper, 
			EmailVerificationTask emailVerificationTask) {
		this.keycloakService = keycloakService;
		this.tempUserRepository = tempUserRepository;
		this.tempUserMapper = tempUserMapper;
		this.emailVerificationTask = emailVerificationTask;
	}

	
	public TempUserDTO createTemporaryUser(TempUserDTO dto) {
		List<TempUser> existingUsers = tempUserRepository.findByAccessUuid(dto.getAccessUuid());
		
		if (existingUsers.size() > 0) {
			throw new UserExistsException("Temporary user already exists");
		}
		
		List<TempUser> existingUsersByUsername = tempUserRepository.findByUsername(dto.getUsername());
		
		if (existingUsersByUsername.size() > 0) {
			throw new UserExistsException("Temporary user already exists with usename: " + dto.getUsername());
		}
		
		TempUser newTempUser = new TempUser();
		newTempUser.setUuid(dto.getUuid());
		newTempUser.setAccessUuid(dto.getAccessUuid());
		newTempUser.setKeycloakUserId(dto.getKeycloakUserId());
		newTempUser.setUsername(dto.getUsername());
		newTempUser.setVerified(dto.isVerified());
		newTempUser.setLocked(dto.isLocked());
		newTempUser.setRequireVerificationOnEveryAccess(dto.isRequireVerificationOnEveryAccess());
		
		TempUser saved = tempUserRepository.save(newTempUser);
		
		return tempUserMapper.toDto(saved);
	}
	
	private TempUserDTO createTempUserFromAccessRequest(AccessRequestDTO accessRequestDto, UUID accessUuid) {
		String username;
		if (accessRequestDto.getUsername() == null || accessRequestDto.getUsername().isBlank()) {
			username = accessRequestDto.getEmail().split("@")[0];
		} else {
			username = accessRequestDto.getUsername();
		}
		
		TempUserDTO tempUserDto = keycloakService.registerTemporaryUser(username, accessUuid.toString());
		tempUserDto.setUuid(UUID.randomUUID());
		tempUserDto.setRequireVerificationOnEveryAccess(accessRequestDto.isRequireVerificationOnEveryAccess());
		
		return createTemporaryUser(tempUserDto);
	}
	
	@Override
	public TemporaryAccessGrantDTO authenticateTemporaryUserAccess(AccessRequestDTO accessRequestDto) {
		LOG.info("Authenticate temporary user access"); 
		
		UUID generatedAccessUuid = UuidUtils.generateType5UUID(encryptionSalt, accessRequestDto.getEmail());
		TempUser tempUser = tempUserRepository.findByAccessUuid(generatedAccessUuid).stream().findFirst()
				.orElseThrow(() -> new UserNotVerifiedException("User is not verified"));
		
		if (tempUser.isLocked()) {
			LOG.info("User is locked, uuid: {}", tempUser.getUuid().toString());
			throw new UserLockedException("User is locked");
		}
		
		if (tempUser.isVerified()) {
			LOG.info("User is verified, uuid: {}", tempUser.getUuid().toString());
			
			if (accessRequestDto.isRequireVerificationOnEveryAccess() != tempUser.isRequireVerificationOnEveryAccess()) {
				tempUser.setRequireVerificationOnEveryAccess(accessRequestDto.isRequireVerificationOnEveryAccess());
				
				if (accessRequestDto.isRequireVerificationOnEveryAccess()) {
					tempUser.setVerified(false);
					tempUserRepository.save(tempUser);
					throw new UserNotVerifiedException("User is not verified");
				}
				
				JwtDTO jwtDto = keycloakService.authenticateTemporaryUser(tempUser.getUsername(), tempUser.getAccessUuid().toString());
				TempUser savedUser = tempUserRepository.save(tempUser);
				return TemporaryAccessGrantDTO.builder()
						.accessToken(jwtDto.getAccessToken())
						.userId(savedUser.getUuid())
						.username(savedUser.getUsername())
						.build();
			}
			
			JwtDTO jwtDto = keycloakService.authenticateTemporaryUser(tempUser.getUsername(), tempUser.getAccessUuid().toString());
			
			if (tempUser.isRequireVerificationOnEveryAccess()) {
				LOG.info("User requires verification on every access, setting verified -> false");
				tempUser.setVerified(false);
				TempUser savedUser = tempUserRepository.save(tempUser);
				return TemporaryAccessGrantDTO.builder()
						.accessToken(jwtDto.getAccessToken())
						.userId(savedUser.getUuid())
						.username(savedUser.getUsername())
						.build();
			}
			
			return TemporaryAccessGrantDTO.builder()
				.accessToken(jwtDto.getAccessToken())
				.userId(tempUser.getUuid())
				.username(tempUser.getUsername())
				.build();
		}
		
		LOG.info("User is not verified, uuid: {}", tempUser.getUuid().toString());
		throw new UserNotVerifiedException("User is not verified, check email for latest verification link");
	}

	@Override
	public TemporaryAccessGrantDTO requestTemporaryUserAccess(AccessRequestDTO accessRequestDto) {
		LOG.info("Requesting temporary user access"); 
		
		UUID generatedAccessUuid = UuidUtils.generateType5UUID(encryptionSalt, accessRequestDto.getEmail());
		Optional<TempUser> tempUserOpt = tempUserRepository.findByAccessUuid(generatedAccessUuid).stream().findFirst();
		
		if (tempUserOpt.isPresent()) {
			TempUser tempUser = tempUserOpt.get();
			if (accessRequestDto.isRequireVerificationOnEveryAccess() != tempUser.isRequireVerificationOnEveryAccess()) {
				tempUser.setRequireVerificationOnEveryAccess(accessRequestDto.isRequireVerificationOnEveryAccess());
				TempUser savedUser = tempUserRepository.save(tempUser);
				return authorizeAndVerifyAccess(accessRequestDto, savedUser);
			} else {
				return authorizeAndVerifyAccess(accessRequestDto, tempUser);
			}
			
		} else {
			TempUserDTO tempUserDto = createTempUserFromAccessRequest(accessRequestDto, generatedAccessUuid);
			createTokenForVerification(accessRequestDto, tempUserDto.getUuid());
			throw new UserNotVerifiedException("User is not verified, check email for latest verification link");
		}
	}
	
	private TemporaryAccessGrantDTO authorizeAndVerifyAccess(AccessRequestDTO accessRequestDto, TempUser tempUser) {
		if (tempUser.isLocked()) {
			LOG.info("User is locked, uuid: {}", tempUser.getUuid().toString());
			throw new UserLockedException("User is locked");
		}
				
		if (tempUser.isVerified()) {
			LOG.info("User is verified, uuid: {}", tempUser.getUuid().toString());			
			JwtDTO jwtDto = keycloakService.authenticateTemporaryUser(tempUser.getUsername(), tempUser.getAccessUuid().toString());
			return TemporaryAccessGrantDTO.builder()
					.accessToken(jwtDto.getAccessToken())
					.userId(tempUser.getUuid())
					.username(tempUser.getUsername())
					.build();
		} else {
			LOG.info("User is not verified, uuid: {}", tempUser.getUuid().toString());
			
			UUID verificationToken = tempUser.getVerificationToken();
			if (verificationToken != null) {
				LOG.info("User already has an verification token with uuid: {}", verificationToken.toString());
				throw new UserNotVerifiedException("User already has a verification token");
			}
			
			createTokenForVerification(accessRequestDto, tempUser.getUuid());
			throw new UserNotVerifiedException("User is not verified, check email for latest verification link");
		}
	}
	
	private void createTokenForVerification(AccessRequestDTO accessRequestDto, UUID userUuid) {
		UUID verificationToken = createVerificationTokenForUser(userUuid);
		if (userUuid != null && verificationToken != null) {
			sendEmailVerification(accessRequestDto, userUuid, verificationToken);
		} else {
			throw new CommonInternalException(
					"failed-to-send-temp-user-verification", 
					"Something went wrong on user verification", 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private UUID createVerificationTokenForUser(UUID userUuid) {
		TempUser user = tempUserRepository.findByUuid(userUuid).stream()
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("temp-user-not-found", "TempUser not found with uuid: " + userUuid));
		
		user.setVerificationToken(UUID.randomUUID());
		
		TempUser savedUser = tempUserRepository.save(user);
		
		return savedUser.getVerificationToken();
	}
	
	public void sendEmailVerification(AccessRequestDTO accessRequestDto, UUID userUuid, UUID verificationToken) {
		emailVerificationTask.execute(accessRequestDto.getEmail(), accessRequestDto.getCallbackUrl(), userUuid, verificationToken);
	}
	
	@Override
	public TempUserDTO verifyTemporaryUser(UUID verificationToken, UUID userUuid) {
		LOG.info("Verifying temporary user from verification token: {}", verificationToken.toString()); 
		
		TempUser user = tempUserRepository.findByVerificationToken(verificationToken).stream()
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("temp-user-not-found", "TempUser not found with verification token: " + verificationToken.toString()));
		
		if (!userUuid.equals(user.getUuid())) {
			throw new CommonInternalException("failed-to-verify-temporary-user", "Failed to verify user uuid: " + userUuid.toString());
		}
		
		keycloakService.verifyTemporaryUserEmail(user.getKeycloakUserId(), user.getUsername());
		user.setVerificationToken(null);
		user.setVerified(true);
		user.setVerifiedAt(LocalDateTime.now());
		TempUser savedUser = tempUserRepository.save(user);
		
		return tempUserMapper.toDto(savedUser);
	}


	@Override
	public void logoutTemporaryUser(LogoutDTO logoutDto) {
		TempUser user = tempUserRepository.findByUuid(logoutDto.getUserId()).stream()
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("temp-user-not-found", "TempUser not found with id: " + logoutDto.getUserId().toString()));
		keycloakService.logoutTemporaryUser(user.getKeycloakUserId(), logoutDto.getUsername());
	}

}
