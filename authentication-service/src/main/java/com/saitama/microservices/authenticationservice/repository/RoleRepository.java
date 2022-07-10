package com.saitama.microservices.authenticationservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.saitama.microservices.authenticationservice.entity.Authority;
import com.saitama.microservices.authenticationservice.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	@Query("SELECT r FROM Role r WHERE authority=:authority")
	public Optional<Role> findRoleByAuthority(@Param("authority") Authority authority);
	
}
