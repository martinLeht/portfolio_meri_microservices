package com.saitama.microservices.blogservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection="tag")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = { "persisted" })
public class Tag implements Persistable<Long>, Comparable<Tag> {
	
	@Transient
	public static final String ID_SEQUENCE = "tag_sequence";

	@Id
	private long id;
	private String postTitle;
	private String postIntro;
	private String contentFlat;
	private Attachment thumbnail;
	private long postId;
	private String userId;
	private boolean persisted;
	
	@CreatedDate
	private LocalDateTime createdAt;


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
}
