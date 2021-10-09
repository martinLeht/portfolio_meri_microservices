package com.saitama.microservices.authenticationservice.controller;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saitama.microservices.authenticationservice.dto.JwtDto;
import com.saitama.microservices.authenticationservice.dto.LoginDTO;
import com.saitama.microservices.authenticationservice.dto.UserDTO;
import com.saitama.microservices.authenticationservice.entity.User;
import com.saitama.microservices.authenticationservice.jwt.JwtUtil;
import com.saitama.microservices.authenticationservice.service.IUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private ModelMapper modelMapper;

	
	@PostMapping("/login")
	public ResponseEntity<JwtDto> login(@RequestBody LoginDTO loginDto) {
		User authenticatedUser = userService.authenticateUser(loginDto);
		
		if (authenticatedUser != null) {
			String token = jwtUtil.generateToken(authenticatedUser.getUsername());
			return new ResponseEntity<JwtDto>(new JwtDto(token), HttpStatus.OK);
		} else {
			return new ResponseEntity<JwtDto>(new JwtDto(), HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDto) {
		
		/* Save user */
		User newUser = userService.registerUser(userDto);
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
