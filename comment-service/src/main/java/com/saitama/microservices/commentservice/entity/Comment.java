package com.saitama.microservices.commentservice.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(value = { "id" })
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_generator")
	@SequenceGenerator(name = "comment_generator", sequenceName = "comment_seq", allocationSize = 3)
	@Column(nullable = false, updatable = false, unique = true)
	private Long id;

	@Column(nullable = false, updatable = false, unique = true)
	private UUID uuid;

	@Column(nullable = false, updatable = false)
	private UUID userId;
	
	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private UUID postId;

	@Column
	private String content;
	
	@Column
	private boolean verified;

	@Column(name = "created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	private Comment parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Comment> children = new ArrayList<Comment>();

	public Comment() {
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

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UUID getPostId() {
		return postId;
	}

	public void setPostId(UUID postId) {
		this.postId = postId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	@JsonIgnore
	public Comment getParent() {
		return parent;
	}

	@JsonIgnore
	public void setParent(Comment parent) {
		this.parent = parent;
	}
	
	@JsonIgnore
	public List<Comment> getChildren() {
		return children;
	}
	
	@JsonIgnore
	public void setChildren(List<Comment> children) {
		this.children = children;
	}

	public void addChild(Comment child) {
		children.add(child);
		child.setParent(this);
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", uuid=" + uuid + ", userId=" + userId + ", children=" + children + ", postId="
				+ postId + ", content=" + content + ", verified=" + verified + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	

}
