package com.saitama.microservices.blogservice.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.saitama.microservices.blogservice.model.Attachment;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogPostDto {
	
	private long id;
	
	private String title;
	
	@JsonFormat(pattern="dd.MM.yyyy HH:mm")
	private LocalDateTime createdAt;
	
	@JsonFormat(pattern="dd.MM.yyyy HH:mm")
	private LocalDateTime updatedAt;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private TagDto tag;
	
	private List<ContentBlockDto> content = new ArrayList<>();
	
	private List<AttachmentDto> attachments = new ArrayList<>();
	
	private String userId;
	
	
	public BlogPostDto() { }


	public BlogPostDto(long id, String title, LocalDateTime createdAt, LocalDateTime updatedAt, TagDto tag, List<ContentBlockDto> content,
			List<AttachmentDto> attachments, String userId) {
		this.id = id;
		this.title = title;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.tag = tag;
		this.content = content;
		this.attachments = attachments;
		this.userId = userId;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
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


	public TagDto getTag() {
		return tag;
	}


	public void setTag(TagDto tag) {
		this.tag = tag;
	}


	public List<ContentBlockDto> getContent() {
		return content;
	}


	public void setContent(List<ContentBlockDto> content) {
		this.content = content;
	}
	
	
	public List<AttachmentDto> getAttachments() {
		return attachments;
	}


	public void setAttachments(List<AttachmentDto> attachments) {
		this.attachments = attachments;
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
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		BlogPostDto other = (BlogPostDto) obj;
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
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
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
		return true;
	}


	@Override
	public String toString() {
		return "BlogPostDto [id=" + id + ", title=" + title + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", tag=" + tag + ", content=" + content + ", attachments=" + attachments + ", userId=" + userId + "]";
	}
	
}
