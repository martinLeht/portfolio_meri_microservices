package com.saitama.microservices.blogservice.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection="blog_post")
public class BlogPost implements Persistable<Long> {
	
	@Transient
	public static final String ID_SEQUENCE = "blog_sequence";

	@Id
	private long id;
	
	private String uuid;
	
	private String title;
	
	@CreatedDate
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	private LocalDateTime updatedAt;
	
	private boolean persisted;
	
	private List<ContentBlock> content = new ArrayList<>();
	
	private List<Attachment> attachments = new ArrayList<>();
	
	private String userId;
	
	
	public BlogPost() {
		
	}


	public BlogPost(long id, String uuid, String title, LocalDateTime createdAt, LocalDateTime updatedAt, List<ContentBlock> content, 
			List<Attachment> attachments, String userId) {
		this.id = id;
		this.uuid = uuid;
		this.title = title;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.content = content;
		this.attachments = attachments;
		this.userId = userId;
	}
	
	
	@Override
	public Long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
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

	public List<ContentBlock> getContent() {
		return content;
	}


	public void setContent(List<ContentBlock> content) {
		this.content = content;
	}
	
	
	public List<Attachment> getAttachments() {
		return attachments;
	}


	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}


	@Override
	@JsonIgnore
	public boolean isNew() {
		return !persisted;
	}
	
	public void setPersisted(boolean persisted) {
        this.persisted = persisted;
    }


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attachments == null) ? 0 : attachments.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (persisted ? 1231 : 1237);
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		BlogPost other = (BlogPost) obj;
		if (attachments == null) {
			if (other.attachments != null)
				return false;
		} else if (!attachments.equals(other.attachments))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (id != other.id)
			return false;
		if (persisted != other.persisted)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
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
		return "BlogPost [id=" + id + ", uuid=" + uuid + ", title=" + title + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", persisted=" + persisted + ", content=" + content + ", attachments="
				+ attachments + ", userId=" + userId + "]";
	}
}
