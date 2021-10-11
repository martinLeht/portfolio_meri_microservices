package com.saitama.microservices.authenticationservice.dto;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import com.saitama.microservices.authenticationservice.entity.Role;

public class UserDTO {
	
	private UUID id;
	
	private String email;
	
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String password;
	
	private Integer loginAttempts;
	
	private boolean verified;
	
	private Set<Role> authorities;
	
	private Timestamp createdAt;
	
	
	public UserDTO() {}


	public UserDTO(UUID id, String email, String username, String firstName, String lastName, String password, Integer loginAttempts, boolean verified,
			Set<Role> authorities, Timestamp createdAt) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.loginAttempts = loginAttempts;
		this.verified = verified;
		this.authorities = authorities;
		this.createdAt = createdAt;
	}


	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}
	
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Integer getLoginAttempts() {
		return loginAttempts;
	}


	public void setLoginAttempts(Integer loginAttempts) {
		this.loginAttempts = loginAttempts;
	}


	public boolean isVerified() {
		return verified;
	}


	public void setVerified(boolean verified) {
		this.verified = verified;
	}


	public Set<Role> getAuthorities() {
		return authorities;
	}


	public void setAuthorities(Set<Role> authorities) {
		this.authorities = authorities;
	}


	public Timestamp getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((loginAttempts == null) ? 0 : loginAttempts.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		UserDTO other = (UserDTO) obj;
		if (authorities == null) {
			if (other.authorities != null)
				return false;
		} else if (!authorities.equals(other.authorities))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
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
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (verified != other.verified)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", email=" + email + ", username=" + username + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", password=" + password + ", loginAttempts=" + loginAttempts
				+ ", verified=" + verified + ", authorities=" + authorities + ", createdAt=" + createdAt + "]";
	}
	
}
