package com.saitama.microservices.portfoliodataservice.entity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.saitama.microservices.portfoliodataservice.constant.ExperienceType;

@Entity
public class Experience {

	@Id
	@GeneratedValue(
	    strategy = GenerationType.SEQUENCE,
	    generator = "experience_generator"
	)
	@SequenceGenerator(
	    name = "experience_generator",
	    sequenceName = "experience_seq",
	    allocationSize = 3
	)
	@Column(nullable = false, updatable = false, unique = true)
	private Long id;

	@Column(nullable = false, updatable = false, unique = true)
	private UUID uuid;

	@Column(nullable = false)
	private UUID userId;

	@Enumerated(EnumType.STRING)
	@Column
	private ExperienceType experienceType;

	@Column
	private String title;

	@Column
	private boolean hidden;

	@Column
	private String shortDescription;

	@Column(length = 2000)
	private String content;

	@Column
	private LocalDate startDate;

	@Column
	private LocalDate endDate;

	@OneToOne(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
	@PrimaryKeyJoinColumn
	private Media media;

	@Column(name = "created_at")
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Timestamp updatedAt;

	public Experience() {
	}

	public Experience(Long id, UUID uuid, UUID userId, ExperienceType experienceType, String title, boolean hidden,
			String shortDescription, String content, LocalDate startDate, LocalDate endDate, Media media,
			Timestamp createdAt, Timestamp updatedAt) {
		this.id = id;
		this.uuid = uuid;
		this.userId = userId;
		this.experienceType = experienceType;
		this.title = title;
		this.hidden = hidden;
		this.shortDescription = shortDescription;
		this.content = content;
		this.startDate = startDate;
		this.endDate = endDate;
		this.media = media;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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

	public ExperienceType getExperienceType() {
		return experienceType;
	}

	public void setExperienceType(ExperienceType experienceType) {
		this.experienceType = experienceType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
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

	@Override
	public String toString() {
		return "Experience [id=" + id + ", uuid=" + uuid + ", userId=" + userId + ", experienceType=" + experienceType
				+ ", title=" + title + ", hidden=" + hidden + ", shortDescription=" + shortDescription + ", content="
				+ content + ", startDate=" + startDate + ", endDate=" + endDate + ", media=" + media + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
