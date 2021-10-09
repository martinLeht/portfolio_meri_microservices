package com.saitama.microservices.blogservice.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class BlogPostDto {

	
	private Long id;
	
	@NotNull
	private String title;
	
	private List<ContentBlockDto> content = new ArrayList<>();

	private String createdAt;
	
	private String updatedAt;
	
	private TagDto tag;
	
	
	public BlogPostDto() {} 
	
	public BlogPostDto(Long id, String title, List<ContentBlockDto> content, String createdAt,
			String updatedAt, TagDto tag) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.tag = tag;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ContentBlockDto> getContent() {
		return content;
	}

	public void setContent(List<ContentBlockDto> content) {
		this.content = content;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public TagDto getTag() {
		return tag;
	}

	public void setTag(TagDto tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "BlogPostDto [id=" + id + ", title=" + title + ", content=" + content + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", tagDto=" + tag + "]";
	}	
	
}
