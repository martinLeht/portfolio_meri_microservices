package com.saitama.microservices.authenticationservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saitama.microservices.authenticationservice.dto.LoginDTO;
import com.saitama.microservices.authenticationservice.dto.RegisterDTO;
import com.saitama.microservices.authenticationservice.dto.UserDTO;
import com.saitama.microservices.authenticationservice.entity.Authority;
import com.saitama.microservices.authenticationservice.entity.Role;
import com.saitama.microservices.authenticationservice.entity.User;
import com.saitama.microservices.authenticationservice.entity.UserDetails;
import com.saitama.microservices.authenticationservice.entity.UserProfile;
import com.saitama.microservices.authenticationservice.exception.AuthenticationException;
import com.saitama.microservices.authenticationservice.exception.RoleMissingException;
import com.saitama.microservices.authenticationservice.exception.UserExistsException;
import com.saitama.microservices.authenticationservice.exception.UserNotFoundException;
import com.saitama.microservices.authenticationservice.repository.RoleRepository;
import com.saitama.microservices.authenticationservice.repository.UserRepository;
import com.saitama.microservices.authenticationservice.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/*
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOpt = this.userRepository.findByUsername(username);
		return userOpt.orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + username));
	}
	*/


	@Override
	public User registerUser(RegisterDTO registerDto) {
		
		Optional<User> userOpt = this.userRepository.findByEmail(registerDto.getEmail());
		if (userOpt.isPresent()) {
			throw new UserExistsException("User with email " + registerDto.getEmail() + " already exists.");
		}
		
		User user = new User();
		user.setEmail(registerDto.getEmail());
		user.setUsername(registerDto.getUsername());
		user.setUuid(UUID.randomUUID());
		
		UserProfile userProfile = new UserProfile();
		userProfile.setFirstName(registerDto.getFirstName());
		userProfile.setLastName(registerDto.getLastName());
		userProfile.setUser(user);
		user.setUserProfile(userProfile);
		
		UserDetails userDetails = new UserDetails();
		userDetails.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		Optional<Role> roleOpt = roleRepository.findRoleByAuthority(Authority.AUTHOR);
		if (roleOpt.isEmpty()) {
			throw new RoleMissingException("Initial role data missing. Cannot assign a role and authority for the new user. "
					+ "Interrupting the registeration, try again later!");
		}
		user.addAuthority(roleOpt.get());
		userDetails.setVerified(true);
		userDetails.setLocked(false);
		userDetails.setAccountExpired(false);
		userDetails.setCredentialsExpired(false);
		userDetails.setLoginAttempts(0);
		userDetails.setUser(user);
		user.setUserDetails(userDetails);
		
		return this.userRepository.save(user);
	}
	
	@Override
	public User authenticateUser(LoginDTO loginDto) {
		Optional<User> userOpt = userRepository.findByEmail(loginDto.getEmail());
		
		if (userOpt.isEmpty()) {
			throw new UserNotFoundException("User not found with email: " + loginDto.getEmail());
		}
		
		User user = userOpt.get();
		
		if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
			return user;
		} else {
			throw new AuthenticationException("User could not be authenticated.");
		}
	}


	@Override
	public List<User> getUsers() {
		return this.userRepository.findAll();
	}


	@Override
	public Optional<User> getUserById(UUID id) {
		return this.userRepository.findByUuid(id);
	}
	
	@Override
	public Optional<User> getUserByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}


	@Override
	public Optional<User> getUserByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}



	@Override
	public User updateUser(UUID id, UserDTO userDto) {
		Optional<User> userToUpdateOpt = this.userRepository.findByUuid(id);
		if (userToUpdateOpt.isPresent()) {
			User userToUpdate = userToUpdateOpt.get();
			mapUserDataToUpdate(userToUpdate, userDto);
			return this.userRepository.save(userToUpdate);
		} else {
			return null;
		}
	}
	
	private void mapUserDataToUpdate(User userToUpdate, UserDTO userDto) {
		String username = userDto.getUsername();
		
		if (username != null) {
			userToUpdate.setUsername(username);
		}
	}


	@Override
	public void deleteUser(UUID id) {
		Optional<User> userOpt = this.userRepository.findByUuid(id);
		userOpt.ifPresentOrElse(
				user -> userRepository.deleteById(user.getId()),
				() -> LOG.info("No user found with provided id: " + id.toString()));
	}
	
}
