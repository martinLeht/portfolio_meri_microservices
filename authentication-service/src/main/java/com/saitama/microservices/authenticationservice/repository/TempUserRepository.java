package com.saitama.microservices.authenticationservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saitama.microservices.authenticationservice.entity.TempUser;

public interface TempUserRepository extends JpaRepository<TempUser, Long> {

	List<TempUser> findByUuid(UUID uuid);
}
