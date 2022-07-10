package com.saitama.microservices.blogservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection="tag")
public class Tag implements Persistable<Long>, Comparable<Tag> {
	
	@Transient
	public static final String ID_SEQUENCE = "tag_sequence";

	@Id
	private long id;
	
	private String postTitle;
	
	private String postIntro;
	
	private Attachment thumbnail;
	
	@CreatedDate
	private LocalDateTime createdAt;

	private long postId;
	
	private String userId;
	
	private boolean persisted;
	
	public Tag() { }

	public Tag(long id, String postTitle, String postIntro, Attachment thumbnail, LocalDateTime createdAt , long postId, String userId) {
		super();
		this.id = id;
		this.postTitle = postTitle;
		this.postIntro = postIntro;
		this.thumbnail = thumbnail;
		this.createdAt = createdAt;
		this.postId = postId;
		this.userId = userId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostIntro() {
		return postIntro;
	}

	public void setPostIntro(String postIntro) {
		this.postIntro = postIntro;
	}

	public Attachment getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Attachment thumbnail) {
		this.thumbnail = thumbnail;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	
	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public int compareTo(Tag tag) {
		return createdAt.compareTo(tag.getCreatedAt());
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	@JsonIgnore
	public boolean isNew() {
		return !persisted;
	}
	
	public void setPersisted(boolean persisted) {
		this.persisted = persisted;
	}
	
	
	
}
