package com.saitama.microservices.portfoliodataservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saitama.microservices.commonlib.constant.MediaType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Media {

	@Id
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column
	private MediaType type;

	@Column
	private String src;

	@Column
	private String name;

	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	@JsonIgnore
	private Experience experience;

}
