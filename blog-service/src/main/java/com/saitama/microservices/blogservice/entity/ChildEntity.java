package com.saitama.microservices.blogservice.entity;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "child_entity")
public class ChildEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "parent_id", referencedColumnName = "parent_id", nullable = true)
	private ParentEntity parent;
	
	
	public ChildEntity() {
		
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public ParentEntity getParent() {
		return parent;
	}


	public void setParent(ParentEntity parent) {
		this.parent = parent;
	}

	
	
	
}
