package com.saitama.microservices.blogservice.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ParentDto {

	private Long id;
	
	private Timestamp createdAt;
	
	private List<ChildDto> childs = new ArrayList<>();
	
	
	public ParentDto() {
		
	}


	public ParentDto(Long id, Timestamp createdAt, List<ChildDto> childs) {
		this.id = id;
		this.createdAt = createdAt;
		this.childs = childs;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Timestamp getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public List<ChildDto> getChilds() {
		return childs;
	}


	public void setChilds(List<ChildDto> childs) {
		this.childs = childs;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((childs == null) ? 0 : childs.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ParentDto other = (ParentDto) obj;
		if (childs == null) {
			if (other.childs != null)
				return false;
		} else if (!childs.equals(other.childs))
			return false;
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
		return true;
	}


	@Override
	public String toString() {
		return "ParentEntity [id=" + id + ", createdAt=" + createdAt + ", childs=" + childs + "]";
	}
	
}
