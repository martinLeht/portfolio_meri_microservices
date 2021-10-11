package com.saitama.microservices.authenticationservice.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saitama.microservices.authenticationservice.dto.JwtDTO;
import com.saitama.microservices.authenticationservice.dto.LoginDTO;
import com.saitama.microservices.authenticationservice.dto.UserDTO;
import com.saitama.microservices.authenticationservice.entity.JwtRefreshToken;
import com.saitama.microservices.authenticationservice.entity.User;
import com.saitama.microservices.authenticationservice.exception.JwtTokenRefreshException;
import com.saitama.microservices.authenticationservice.service.IJwtService;
import com.saitama.microservices.authenticationservice.service.IUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IJwtService jwtService;
	
	@Autowired
	private ModelMapper modelMapper;

	
	@PostMapping("/login")
	public ResponseEntity<JwtDTO> login(@RequestBody LoginDTO loginDto) {
		User authenticatedUser = userService.authenticateUser(loginDto);
		
		if (authenticatedUser != null) {
			String accessToken = jwtService.getJwtToken(authenticatedUser.getId().toString());
			JwtRefreshToken refreshToken = jwtService.getRefreshToken(authenticatedUser.getId().toString());
			return new ResponseEntity<JwtDTO>(new JwtDTO(accessToken, refreshToken.getToken()), HttpStatus.OK);
		} else {
			return new ResponseEntity<JwtDTO>(new JwtDTO(), HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	@PostMapping("/token/refresh")
	public ResponseEntity<JwtDTO> refreshToken(@RequestBody JwtDTO jwtDto) {
		String refreshToken = jwtDto.getRefreshToken();					
		
		return jwtService.getRefreshTokenByToken(refreshToken)
				.map(jwtService::verifyRefreshTokenValidity)
				.map(JwtRefreshToken::getUser)
				.map(user -> {
					String token = jwtService.getJwtToken(user.getId().toString());
					return new ResponseEntity<JwtDTO>(new JwtDTO(token, refreshToken), HttpStatus.OK);
				})
				.orElseThrow(() -> new JwtTokenRefreshException(refreshToken, "Refresh token missing from database!"));
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDto) {
		LOG.info("Päästiin sisään");
		/* Save user */
		User newUser = userService.registerUser(userDto);
		newUser.setPassword(null);
		System.out.println(newUser);
		
		UserDTO newUserDto = mapUserToDto(newUser);
		return new ResponseEntity<UserDTO>(newUserDto, HttpStatus.OK);
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
