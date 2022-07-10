package com.saitama.microservices.authenticationservice.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="auth_user")
public class User implements org.springframework.security.core.userdetails.UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3075540582906344805L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false, unique = true)
	private Long id;

	@Column(nullable = false, updatable = false, unique = true)
	private UUID uuid;
	
	@Column
	private String email;
	
	@Column
	private String username;
	
	@Column(name = "created_at")
	@CreationTimestamp
	private Timestamp createdAt;
	
	@Column(name = "updated_at")
	@UpdateTimestamp
	private Timestamp updatedAt;
	
	@ManyToMany
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
	)
	private Set<Role> authorities;
	
	@OneToOne(mappedBy = "user")
	private JwtRefreshToken refreshToken;
	
	@OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "user")
	private UserProfile userProfile;
	
	@OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "user")
	private UserDetails userDetails;
	
	public User() { }
	

	public User(long id, UUID uuid, String email, String username, 
				Timestamp createdAt, Timestamp updatedAt, Set<Role> authorities, JwtRefreshToken refreshToken) {
		this.id = id;
		this.uuid = uuid;
		this.email = email;
		this.username = username;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.authorities = authorities;
		this.refreshToken = refreshToken;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public UUID getUuid() {
		return uuid;
	}


	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
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


	public void addAuthority(Role authority) {
		if (this.authorities == null) {
			this.authorities = new HashSet<Role>();
		}
		this.authorities.add(authority);
	}

	public void setAuthorities(Set<Role> authorities) {
		this.authorities = authorities;
	}
	
	public JwtRefreshToken getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(JwtRefreshToken refreshToken) {
		this.refreshToken = refreshToken;
	}

	@JsonIgnore
	public UserProfile getUserProfile() {
		return userProfile;
	}


	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}


	public UserDetails getUserDetails() {
		return userDetails;
	}

	@JsonIgnore
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}


	@Override
	public Set<Role> getAuthorities() {
		return authorities;
	}


	@Override
	@JsonIgnore
	public String getPassword() {
		return userDetails.getPassword();
	}
	

	@Override
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}


	@Override
	public boolean isAccountNonExpired() {
		return userDetails.isAccountNonExpired();
	}


	@Override
	public boolean isAccountNonLocked() {
		return userDetails.isAccountNonLocked();
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return userDetails.isCredentialsNonExpired();
	}


	@Override
	public boolean isEnabled() {
		return userDetails.isVerified();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((refreshToken == null) ? 0 : refreshToken.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((userDetails == null) ? 0 : userDetails.hashCode());
		result = prime * result + ((userProfile == null) ? 0 : userProfile.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		User other = (User) obj;
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
		if (id != other.id)
			return false;
		if (refreshToken == null) {
			if (other.refreshToken != null)
				return false;
		} else if (!refreshToken.equals(other.refreshToken))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (userDetails == null) {
			if (other.userDetails != null)
				return false;
		} else if (!userDetails.equals(other.userDetails))
			return false;
		if (userProfile == null) {
			if (other.userProfile != null)
				return false;
		} else if (!userProfile.equals(other.userProfile))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", uuid=" + uuid + ", email=" + email + ", username=" + username + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", authorities=" + authorities + ", refreshToken="
				+ refreshToken + ", userProfile=" + userProfile + ", userDetails=" + userDetails + "]";
	}

}
