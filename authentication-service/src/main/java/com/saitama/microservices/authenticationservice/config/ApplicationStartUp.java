package com.saitama.microservices.authenticationservice.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.saitama.microservices.authenticationservice.entity.Role;
import com.saitama.microservices.authenticationservice.repository.RoleRepository;

@Component
public class ApplicationStartUp {

	
	@Autowired
	RoleRepository roleRepository;
	
	@Bean
	public CommandLineRunner loadRoles() {
		return (args) -> {
			List<Role> roles = roleRepository.findAll();
			
			if(ObjectUtils.isEmpty(roles)) {
				Role userRole = new Role(Role.USER);
				Role authorRole = new Role(Role.AUTHOR);
				Role adminRole = new Role(Role.ADMIN);
				this.roleRepository.saveAll(Arrays.asList(userRole, authorRole, adminRole));
			}
		};
	}
	
}
