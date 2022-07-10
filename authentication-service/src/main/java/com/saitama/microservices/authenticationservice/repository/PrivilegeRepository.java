package com.saitama.microservices.authenticationservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.saitama.microservices.authenticationservice.entity.Privilege;
import com.saitama.microservices.authenticationservice.entity.PrivilegeType;


public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
	
	@Query("SELECT p FROM Privilege p WHERE name=:name")
	public Optional<Privilege> findPrivilegeByName(@Param("name") PrivilegeType name);

}
