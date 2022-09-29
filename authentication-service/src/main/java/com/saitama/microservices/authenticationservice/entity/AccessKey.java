package com.saitama.microservices.authenticationservice.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.saitama.microservices.authenticationservice.constant.AccessType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AccessKey {

	@Id
	@GeneratedValue(
	    strategy = GenerationType.SEQUENCE,
	    generator = "access_key_generator"
	)
	@SequenceGenerator(
	    name = "access_key_generator",
	    sequenceName = "access_key_seq",
	    allocationSize = 3
	)
	@Column(nullable = false, updatable = false, unique = true)
	private Long id;
	
	@Column(name = "access_key")
	private UUID accessKey;
	
	@Column
	@Enumerated(EnumType.STRING)
	private AccessType type;
	
	@Column(name = "created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", referencedColumnName = "id")
	private TempUser user;
}
