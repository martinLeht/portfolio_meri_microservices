package com.saitama.microservices.blogservice.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "tag")
public class Tag {

	@Id
	private Long id;
	
	@Column(name = "post_title")
	private String postTitle;
	
	@Column(name = "post_intro")
	private String postIntro;
	
	@Column(name = "thumbnail", length = 1000)
	private String thumbnail;
	
	@Column(name = "created_at")
	@CreationTimestamp
	private Timestamp createdAt;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private BlogPost post;
	
	public Tag() { }

	public Tag(Long id, String postTitle, String postIntro, String thumbnail, Timestamp createdAt, BlogPost post) {
		super();
		this.id = id;
		this.postTitle = postTitle;
		this.postIntro = postIntro;
		this.thumbnail = thumbnail;
		this.createdAt = createdAt;
		this.post = post;
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

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public BlogPost getPost() {
		return post;
	}

	public void setPost(BlogPost post) {
		this.post = post;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((post == null) ? 0 : post.hashCode());
		result = prime * result + ((postIntro == null) ? 0 : postIntro.hashCode());
		result = prime * result + ((postTitle == null) ? 0 : postTitle.hashCode());
		result = prime * result + ((thumbnail == null) ? 0 : thumbnail.hashCode());
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
		Tag other = (Tag) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (post == null) {
			if (other.post != null)
				return false;
		} else if (!post.equals(other.post))
			return false;
		if (postIntro == null) {
			if (other.postIntro != null)
				return false;
		} else if (!postIntro.equals(other.postIntro))
			return false;
		if (postTitle == null) {
			if (other.postTitle != null)
				return false;
		} else if (!postTitle.equals(other.postTitle))
			return false;
		if (thumbnail == null) {
			if (other.thumbnail != null)
				return false;
		} else if (!thumbnail.equals(other.thumbnail))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", postTitle=" + postTitle + ", postIntro=" + postIntro + ", thumbnail=" + thumbnail
				+ ", createdAt=" + createdAt + "]";
	}
	
	
}
