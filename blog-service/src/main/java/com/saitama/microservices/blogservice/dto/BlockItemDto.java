package com.saitama.microservices.blogservice.dto;

import java.util.List;

public class BlockItemDto {
	
	private Long id;
	
	private String type;
	
	private Long orderNumber;
	
	private List<TextFragmentDto> textFragments;
	
	public BlockItemDto() {}
	
	public BlockItemDto(Long id, String type, Long orderNumber, List<TextFragmentDto> textFragments) {
		super();
		this.id = id;
		this.type = type;
		this.orderNumber = orderNumber;
		this.textFragments = textFragments;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<TextFragmentDto> getTextFragments() {
		return textFragments;
	}

	public void setTextFragments(List<TextFragmentDto> textFragments) {
		this.textFragments = textFragments;
	}

	@Override
	public String toString() {
		return "BlockItemDto [id=" + id + ", type=" + type + ", orderNumber=" + orderNumber + ", textFragments="
				+ textFragments + "]";
	}
	
}
