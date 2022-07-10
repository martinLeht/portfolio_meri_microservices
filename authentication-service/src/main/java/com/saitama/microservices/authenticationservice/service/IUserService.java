package com.saitama.microservices.authenticationservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.saitama.microservices.authenticationservice.dto.LoginDTO;
import com.saitama.microservices.authenticationservice.dto.RegisterDTO;
import com.saitama.microservices.authenticationservice.dto.UserDTO;
import com.saitama.microservices.authenticationservice.entity.User;

public interface IUserService extends UserDetailsService {
	
	public User registerUser(RegisterDTO registerDto);
	
	public User authenticateUser(LoginDTO loginDto);
	
	public List<User> getUsers();
	
	public Optional<User> getUserById(UUID id);
	
	public Optional<User> getUserByUsername(String username);
	
	public Optional<User> getUserByEmail(String email);
	
	public User updateUser(UUID id, UserDTO userDto);
	
	public void deleteUser(UUID id);

}
