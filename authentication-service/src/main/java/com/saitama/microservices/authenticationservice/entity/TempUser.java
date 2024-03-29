package com.saitama.microservices.authenticationservice.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = { "verificationToken" })
public class TempUser {
	
	@Id
	@GeneratedValue(
	    strategy = GenerationType.SEQUENCE,
	    generator = "temp_user_generator"
	)
	@SequenceGenerator(
	    name = "temp_user_generator",
	    sequenceName = "temp_user_seq",
	    allocationSize = 3
	)
	@Column(nullable = false, updatable = false, unique = true)
	private Long id;
	
	@Column(nullable = false, updatable = false, unique = true)
	private UUID uuid;
	
	@Column(nullable = false, updatable = false, unique = true)
	private UUID accessUuid;
	
	@Column(name = "keycloak_user_id", nullable = false, unique = true)
	private String keycloakUserId;
	
	@Column(name = "verification_token", unique = true)
	private UUID verificationToken;
	
	@Column
	private String username;
	
	@Column
	private boolean verified;
	
	@Column(name = "verified_at")
	private LocalDateTime verifiedAt;
	
	@Column
	private boolean locked;
	
	@Column(name = "require_verification_on_every_access")
	private boolean requireVerificationOnEveryAccess;
	
	@Column(name = "created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;

}
