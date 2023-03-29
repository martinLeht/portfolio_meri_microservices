package com.saitama.microservices.blogservice.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

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
public class Tag extends UuidIdentified implements Comparable<Tag> {

	private String postTitle;
	private String postIntro;
	private String contentFlat;
	private Attachment thumbnail;
	private UUID userId;
	private LocalDateTime createdAt = LocalDateTime.now();

	@Override
	public int compareTo(Tag tag) {
		return createdAt.compareTo(tag.getCreatedAt());
	}
}
