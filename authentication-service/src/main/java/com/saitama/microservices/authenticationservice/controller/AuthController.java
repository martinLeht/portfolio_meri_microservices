package com.saitama.microservices.authenticationservice.controller;

/*
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saitama.microservices.authenticationservice.dto.JwtDTO;
import com.saitama.microservices.authenticationservice.dto.LoginDTO;
import com.saitama.microservices.authenticationservice.dto.RegisterDTO;
import com.saitama.microservices.authenticationservice.dto.UserDTO;
import com.saitama.microservices.authenticationservice.entity.Authority;
import com.saitama.microservices.authenticationservice.entity.JwtRefreshToken;
import com.saitama.microservices.authenticationservice.entity.Role;
import com.saitama.microservices.authenticationservice.entity.User;
import com.saitama.microservices.authenticationservice.exception.JwtTokenRefreshException;
import com.saitama.microservices.authenticationservice.service.IJwtService;
import com.saitama.microservices.authenticationservice.service.IUserService;

//@RestController
//@RequestMapping("/auth")
public class AuthController {
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
	
	private final IUserService userService;
	private final IJwtService jwtService;
	private final ModelMapper modelMapper;
	
	@Autowired
	public AuthController(
			IUserService userService, 
			IJwtService jwtService,
			ModelMapper modelMapper
	) {
		this.userService = userService;
		this.jwtService = jwtService;
		this.modelMapper = modelMapper;
	}

	
	//@PostMapping("/login")
	public ResponseEntity<JwtDTO> login(@RequestBody LoginDTO loginDto) {
		User authenticatedUser = userService.authenticateUser(loginDto);
		if (authenticatedUser != null) {
			jwtService.deleteByUser(authenticatedUser);
			Set<Authority> authorities = authenticatedUser.getAuthorities().stream().map(Role::getAuthorityEnum).collect(Collectors.toSet());
			String accessToken = jwtService.getJwtToken(authenticatedUser.getUsername(), authenticatedUser.getUuid().toString(), authorities);
			JwtRefreshToken refreshToken = jwtService.getRefreshToken(authenticatedUser.getUuid().toString());
			return ResponseEntity.ok(new JwtDTO(accessToken, refreshToken.getToken()));
		} else {
			return new ResponseEntity<JwtDTO>(new JwtDTO(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	//@PostMapping("/token/refresh")
	public ResponseEntity<JwtDTO> refreshToken(@RequestBody JwtDTO jwtDto) {
		String refreshToken = jwtDto.getRefreshToken();					
		
		return jwtService.getRefreshTokenByToken(refreshToken)
				.map(jwtService::verifyRefreshTokenValidity)
				.map(JwtRefreshToken::getUser)
				.map(user -> {
					Set<Authority> authorities = user.getAuthorities().stream().map(Role::getAuthorityEnum).collect(Collectors.toSet());
					String token = jwtService.getJwtToken(user.getUsername(), user.getUuid().toString(), authorities);
					return new ResponseEntity<JwtDTO>(new JwtDTO(token, refreshToken), HttpStatus.OK);
				})
				.orElseThrow(() -> new JwtTokenRefreshException(refreshToken, "Refresh token missing!"));
	}
	
	//@PostMapping("/register")
	public ResponseEntity<UserDTO> register(@RequestBody RegisterDTO registerDto) {
		// Save user 
		User newUser = userService.registerUser(registerDto);
		
		return ResponseEntity.ok(mapUserToDto(newUser));
	}
	
	//@GetMapping("/user/{uuid}")
	public ResponseEntity<UserDTO> getUserByUuid(@PathVariable String uuid) {
		User user = userService.getUserByUuid(UUID.fromString(uuid));
		
		return ResponseEntity.ok(mapUserToDto(user));
	}
	
	private User mapUserDtoToEntity(UserDTO userDto) {
		User user = modelMapper.map(userDto, User.class);
		return user;
	}
	
	private UserDTO mapUserToDto(User user) {
		UserDTO userDto = modelMapper.map(user, UserDTO.class);
		return userDto;
	}
}
*/
