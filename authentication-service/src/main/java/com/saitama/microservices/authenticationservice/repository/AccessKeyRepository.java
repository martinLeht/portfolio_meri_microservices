package com.saitama.microservices.authenticationservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saitama.microservices.authenticationservice.entity.AccessKey;

public interface AccessKeyRepository extends JpaRepository<AccessKey, Long> {

	
	List<AccessKey> findByAccessKey(UUID accessKey);
}
