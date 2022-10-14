package com.saitama.microservices.authenticationservice.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.saitama.microservices.authenticationservice.constant.AccessType;
import com.saitama.microservices.authenticationservice.dto.AccessKeyDTO;
import com.saitama.microservices.authenticationservice.dto.AccessVerificationDTO;
import com.saitama.microservices.authenticationservice.dto.TempUserDTO;
import com.saitama.microservices.authenticationservice.entity.AccessKey;
import com.saitama.microservices.authenticationservice.entity.TempUser;
import com.saitama.microservices.authenticationservice.exception.UserExistsException;
import com.saitama.microservices.authenticationservice.exception.UserLockedException;
import com.saitama.microservices.authenticationservice.exception.UserNotVerifiedException;
import com.saitama.microservices.authenticationservice.mapper.TempUserMapper;
import com.saitama.microservices.authenticationservice.repository.AccessKeyRepository;
import com.saitama.microservices.authenticationservice.repository.TempUserRepository;
import com.saitama.microservices.authenticationservice.service.ITempUserService;
import com.saitama.microservices.commonlib.dto.MessageDTO;
import com.saitama.microservices.commonlib.dto.PageRequestDTO;
import com.saitama.microservices.commonlib.dto.PaginationDTO;
import com.saitama.microservices.commonlib.kafka.EventType;
import com.saitama.microservices.commonlib.kafka.KafkaMessagingClient;
import com.saitama.microservices.commonlib.util.UuidUtils;

@Service
public class TempUserServiceImpl implements ITempUserService {
	
	private static final Logger LOG = LoggerFactory.getLogger(TempUserServiceImpl.class);
	
	private final TempUserRepository tempUserRepository;
	private final AccessKeyRepository accessKeyRepository;
	private final TempUserMapper tempUserMapper;
	private final KafkaMessagingClient kafkaMessagingClient;
	

	@Value("encryption.salt")
	private String encryptionSalt;
	
	@Autowired
	public TempUserServiceImpl(
			TempUserRepository tempUserRepository,
			AccessKeyRepository accessKeyRepository,
			TempUserMapper tempUserMapper, 
			KafkaMessagingClient kafkaMessagingClient) {
		this.tempUserRepository = tempUserRepository;
		this.accessKeyRepository = accessKeyRepository;
		this.tempUserMapper = tempUserMapper;
		this.kafkaMessagingClient = kafkaMessagingClient;
	}
	

	@Override
	public PaginationDTO<TempUserDTO> getPaginated(PageRequestDTO pageDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TempUserDTO getById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TempUserDTO create(TempUserDTO dto) {
		List<TempUser> existingUsers = tempUserRepository.findByUuid(dto.getUuid());
		
		if (existingUsers.size() > 0) {
			throw new UserExistsException("Temporary user already exists");
		}
		
		TempUser newTempUser = new TempUser();
		newTempUser.setUuid(dto.getUuid());
		newTempUser.setUsername(dto.getUsername());
		newTempUser.setVerified(false);
		newTempUser.setLocked(false);
		
		TempUser saved = tempUserRepository.save(newTempUser);
		
		return tempUserMapper.toDto(saved);
	}

	@Override
	public TempUserDTO update(UUID id, TempUserDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(UUID id) {
		// TODO Auto-generated method stub

	}

	@Override
	public TempUserDTO verifyUserAccess(AccessVerificationDTO accessVerificationDto) {
		LOG.info("Verifying temporary user access"); 
		
		UUID generatedUuid = UuidUtils.generateType5UUID(accessVerificationDto.getEmail(), encryptionSalt);
		Optional<TempUser> tempUserOpt = tempUserRepository.findByUuid(generatedUuid).stream().findFirst();
		
		if (tempUserOpt.isPresent()) {
			TempUser tempUser = tempUserOpt.get();
			
			if (tempUser.isLocked()) {
				LOG.info("User is locked, uuid: {}", generatedUuid.toString());  
				throw new UserLockedException("User is locked");
			} else if (!tempUser.isVerified()) {
				// TODO: Send email verification and throw use is not verified (401)
				LOG.info("User is not verified, uuid: {}", generatedUuid.toString());
				if (tempUser.getAccessKey() != null) {
					LOG.info("User already has a access token with uuid key: " + tempUser.getAccessKey().toString());
					return null;
				} else {
					AccessKey accessToken = createAccessKeyForUser(tempUser);
					
					MessageDTO verificationMessageDto = MessageDTO.builder()
							.to(accessVerificationDto.getEmail())
							.subject("Temporary user verification")
							.content("Verify temporary user access by clicking the link: <URL> " + accessToken.getAccessKey().toString()
									+ "\n\nAfter successful verification, you will be authorized to leave comments on the blog section."
									+ "\nNOTE: Verified access will expire after 30 days of inactivity in the platform. "
									+ "After expiration you will need to verify the access again.")
							.createdAt(LocalDateTime.now())
							.build();
					
					kafkaMessagingClient.sendMessageToChannel(EventType.EMAIL_EVENT, verificationMessageDto);
					throw new UserNotVerifiedException("User is not verified, check email for latest verification link");
				}
			} else {
				LOG.info("User is verified, uuid: {}", generatedUuid.toString());
				return tempUserMapper.toDto(tempUser);
			}
		} else {
			// TODO: Create temporary user, send email verification link.
			String username;
			if (accessVerificationDto.getUsername() == null || accessVerificationDto.getUsername().isBlank()) {
				username= accessVerificationDto.getEmail().split("@")[0];
			} else {
				username = accessVerificationDto.getUsername();
			}
			
			TempUserDTO tempUserDto = TempUserDTO.builder()
					.uuid(generatedUuid)
					.username(username)
					.build();
			
			return create(tempUserDto);
		}
	}
	
	private AccessKey createAccessKeyForUser(TempUser user) {
		AccessKey newAccessKey = new AccessKey();
		
		/*
		UUID accessKeyValue = UUID.randomUUID();
		
		List<AccessKey> existingKeys = accessKeyRepository.findByAccessKey(accessKeyValue);
		
		if (existingKeys.isEmpty()) {
			newAccessKey.setAccessKey(accessKeyValue);
		} else {
			newAccessKey.setAccessKey(UUID.randomUUID());
		}
		*/
		newAccessKey.setAccessKey(UUID.randomUUID());
		newAccessKey.setType(AccessType.TEMPORARY);
		newAccessKey.setUser(user);
		user.setAccessKey(newAccessKey);
		
		TempUser savedUser = tempUserRepository.save(user);
		//AccessKey savedAccessToken = accessKeyRepository.save(newAccessKey);
		
		return savedUser.getAccessKey();
		
	}

}
