package com.saitama.microservices.authenticationservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saitama.microservices.authenticationservice.dto.LoginDTO;
import com.saitama.microservices.authenticationservice.dto.UserDTO;
import com.saitama.microservices.authenticationservice.entity.Role;
import com.saitama.microservices.authenticationservice.entity.User;
import com.saitama.microservices.authenticationservice.exception.AuthenticationException;
import com.saitama.microservices.authenticationservice.exception.RoleMissingException;
import com.saitama.microservices.authenticationservice.exception.UserExistsException;
import com.saitama.microservices.authenticationservice.exception.UserNotFoundException;
import com.saitama.microservices.authenticationservice.repository.RoleRepository;
import com.saitama.microservices.authenticationservice.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOpt = this.userRepository.findByUsername(username);
		return userOpt.orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + username));
	}


	@Override
	public User registerUser(UserDTO userDto) {
		
		Optional<User> userOpt = this.userRepository.findByEmail(userDto.getEmail());
		if (userOpt.isPresent()) {
			throw new UserExistsException("User with email " + userDto.getEmail() + " already exists.");
		}
		
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setUsername(userDto.getUsername());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Optional<Role> roleOpt = roleRepository.findRoleByAuthority(Role.USER);
		if (roleOpt.isEmpty()) {
			throw new RoleMissingException("Initial role data missing. Cannot assign a role and authority for the new user. "
					+ "Interrupting the registeration, try again later!");
		}
		user.addAuthority(roleOpt.get());
		user.setVerified(true);
		
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
		return this.userRepository.findById(id);
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
		User userToUpdate = this.userRepository.getById(id);
		if (userToUpdate != null) {
			mapUserDataToUpdate(userToUpdate, userDto);
			return this.userRepository.save(userToUpdate);
		} else {
			return null;
		}
	}
	
	private void mapUserDataToUpdate(User userToUpdate, UserDTO userDto) {
		String username = userDto.getUsername();
		String firstName = userDto.getFirstName();
		String lastName = userDto.getLastName();
		Integer loginAttempts = userDto.getLoginAttempts();
		
		if (username != null) {
			userToUpdate.setUsername(username);
		}
		if (firstName != null) {
			userToUpdate.setFirstName(firstName);
		}
		if (lastName != null) {
			userToUpdate.setLastName(lastName);
		}
		if (loginAttempts != null) {
			userToUpdate.setLoginAttempts(loginAttempts);
		}
	}


	@Override
	public void deleteUser(UUID id) {
		Optional<User> userOpt = this.userRepository.findById(id);
		userOpt.ifPresentOrElse(
				user -> userRepository.deleteById(id),
				() -> LOG.info("No user found with provided id: " + id.toString()));
	}
	
}
