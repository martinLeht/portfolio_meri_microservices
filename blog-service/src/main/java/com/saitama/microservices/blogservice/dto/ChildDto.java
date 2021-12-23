package com.saitama.microservices.blogservice.dto;

public class ChildDto {

	private Long id;
	
	
	public ChildDto() {
		
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
	

	@Override
	public String toString() {
		return "ChildDto [id=" + id + "]";
	}
	
	
}
