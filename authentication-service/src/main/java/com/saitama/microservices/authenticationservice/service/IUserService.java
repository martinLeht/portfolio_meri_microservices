package com.saitama.microservices.authenticationservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.saitama.microservices.authenticationservice.dto.LoginDTO;
import com.saitama.microservices.authenticationservice.dto.RegisterDTO;
import com.saitama.microservices.authenticationservice.dto.UserDTO;
import com.saitama.microservices.authenticationservice.entity.User;

public interface IUserService /*extends UserDetailsService*/ {
	
	User registerUser(RegisterDTO registerDto);
	
	User authenticateUser(LoginDTO loginDto);
	
	List<User> getUsers();
	
	Optional<User> getUserById(UUID id);
	
	Optional<User> getUserByUsername(String username);
	
	Optional<User> getUserByEmail(String email);
	
	User updateUser(UUID id, UserDTO userDto);
	
	void deleteUser(UUID id);

}
