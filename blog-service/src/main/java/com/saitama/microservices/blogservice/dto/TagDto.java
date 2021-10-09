package com.saitama.microservices.blogservice.dto;

import java.sql.Timestamp;

import com.saitama.microservices.blogservice.entity.BlogPost;

public class TagDto {
	
	private Long id;
	
	private String postTitle;
	
	private String postIntro;
	
	private String thumbnail;
	
	private String createdAt;
	
	public TagDto() { }

	public TagDto(Long id, String postTitle, String postIntro, String thumbnail, String createdAt) {
		super();
		this.id = id;
		this.postTitle = postTitle;
		this.postIntro = postIntro;
		this.thumbnail = thumbnail;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
	

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "TagDto [id=" + id + ", postTitle=" + postTitle + ", postIntro=" + postIntro + ", thumbnail=" + thumbnail
				+ ", createdAt=" + createdAt + "]";
	}

	
}
