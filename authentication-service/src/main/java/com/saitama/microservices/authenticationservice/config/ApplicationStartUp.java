package com.saitama.microservices.authenticationservice.config;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.saitama.microservices.authenticationservice.entity.Authority;
import com.saitama.microservices.authenticationservice.entity.Privilege;
import com.saitama.microservices.authenticationservice.entity.PrivilegeType;
import com.saitama.microservices.authenticationservice.entity.Role;
import com.saitama.microservices.authenticationservice.entity.User;
import com.saitama.microservices.authenticationservice.entity.UserDetails;
import com.saitama.microservices.authenticationservice.entity.UserProfile;
import com.saitama.microservices.authenticationservice.exception.RoleMissingException;
import com.saitama.microservices.authenticationservice.repository.PrivilegeRepository;
import com.saitama.microservices.authenticationservice.repository.RoleRepository;
import com.saitama.microservices.authenticationservice.repository.UserRepository;

@Component
public class ApplicationStartUp {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public CommandLineRunner loadRoles() {
		return (args) -> {
			List<Privilege> privileges = this.privilegeRepository.findAll();
			List<Role> roles = this.roleRepository.findAll();
			
			if(ObjectUtils.isEmpty(roles) && ObjectUtils.isEmpty(privileges)) {
				Privilege read = new Privilege(PrivilegeType.READ);
				Privilege write = new Privilege(PrivilegeType.WRITE);
				Privilege delete = new Privilege(PrivilegeType.DELETE);
				Privilege config = new Privilege(PrivilegeType.CONFIG);
				
				this.privilegeRepository.saveAll(Arrays.asList(read, write, delete, config));
				
				Optional<Privilege> readPrivilege = this.privilegeRepository.findPrivilegeByName(PrivilegeType.READ);
				Optional<Privilege> writePrivilege = this.privilegeRepository.findPrivilegeByName(PrivilegeType.WRITE);
				Optional<Privilege> deletePrivilege = this.privilegeRepository.findPrivilegeByName(PrivilegeType.DELETE);
				Optional<Privilege> configPrivilege = this.privilegeRepository.findPrivilegeByName(PrivilegeType.CONFIG);
				
				Role userRole = new Role(Authority.USER);
				userRole.addPrivilege(readPrivilege.get());
				userRole.addPrivilege(writePrivilege.get());
				
				Role authorRole = new Role(Authority.AUTHOR);
				authorRole.addPrivilege(readPrivilege.get());
				authorRole.addPrivilege(writePrivilege.get());
				authorRole.addPrivilege(deletePrivilege.get());
				
				Role adminRole = new Role(Authority.ADMIN);
				adminRole.addPrivilege(readPrivilege.get());
				adminRole.addPrivilege(writePrivilege.get());
				adminRole.addPrivilege(deletePrivilege.get());
				adminRole.addPrivilege(configPrivilege.get());
				
				this.roleRepository.saveAll(Arrays.asList(userRole, authorRole, adminRole));
				
				
				User user = new User();
				user.setEmail("admin@dev.com");
				user.setUsername("admin666");
				user.setUuid(UUID.randomUUID());
				
				UserProfile userProfile = new UserProfile();
				userProfile.setFirstName("Admin");
				userProfile.setLastName("User");
				userProfile.setUser(user);
				user.setUserProfile(userProfile);
				
				UserDetails userDetails = new UserDetails();
				userDetails.setPassword(passwordEncoder.encode("test123"));
				Optional<Role> roleOpt = roleRepository.findRoleByAuthority(Authority.ADMIN);
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
				
				this.userRepository.save(user);
			}
		};
	}
	
}
