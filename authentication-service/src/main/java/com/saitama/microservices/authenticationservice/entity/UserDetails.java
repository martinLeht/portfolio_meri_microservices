package com.saitama.microservices.authenticationservice.entity;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class UserDetails {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(nullable = false, updatable = false, unique = true)
	private UUID id;
	
	@Column
	private String password;
	
	@Column(name = "login_attempts")
	private Integer loginAttempts;
	
	@Column
	private boolean verified;
	
	@Column
	private boolean locked;
	
	@Column(name = "account_expired")
	private boolean accountExpired;
	
	@Column(name = "credentials_expired")
	private boolean credentialsExpired;
	
	@Column(name = "created_at")
	@CreationTimestamp
	private Timestamp createdAt;
	
	@Column(name = "updated_at")
	@UpdateTimestamp
	private Timestamp updatedAt;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;
	

	public Integer getLoginAttempts() {
		return loginAttempts;
	}
	
	public void setLoginAttempts(Integer loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	
	public UserDetails() { }
	

	public UserDetails(UUID id, String password, Integer loginAttempts, boolean verified, boolean locked,
			boolean accountExpired, boolean credentialsExpired, Timestamp createdAt, Timestamp updatedAt) {
		this.id = id;
		this.password = password;
		this.loginAttempts = loginAttempts;
		this.verified = verified;
		this.locked = locked;
		this.accountExpired = accountExpired;
		this.credentialsExpired = credentialsExpired;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isAccountNonExpired() {
		return !accountExpired;
	}
	
	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public boolean isAccountNonLocked() {
		return !locked;
	}
	
	public void setLocked(boolean locked) {
		this.locked = locked;
	}	
	
	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	
	public Timestamp getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}


	public Timestamp getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (accountExpired ? 1231 : 1237);
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + (credentialsExpired ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (locked ? 1231 : 1237);
		result = prime * result + ((loginAttempts == null) ? 0 : loginAttempts.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + (verified ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDetails other = (UserDetails) obj;
		if (accountExpired != other.accountExpired)
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (credentialsExpired != other.credentialsExpired)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (locked != other.locked)
			return false;
		if (loginAttempts == null) {
			if (other.loginAttempts != null)
				return false;
		} else if (!loginAttempts.equals(other.loginAttempts))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (verified != other.verified)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", password=" + password + ", loginAttempts=" + loginAttempts + ", verified="
				+ verified + ", locked=" + locked + ", accountExpired=" + accountExpired + ", credentialsExpired="
				+ credentialsExpired + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
}
