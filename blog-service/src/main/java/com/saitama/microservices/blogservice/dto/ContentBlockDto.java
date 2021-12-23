package com.saitama.microservices.blogservice.dto;

import java.util.List;

public class ContentBlockDto implements Comparable<ContentBlockDto>{

	private Long id;
	
	private String type;
	
	private Long orderNumber;
	
	private List<BlockItemDto> blockItems;
	
	
	public ContentBlockDto() {}
	
	public ContentBlockDto(Long id, String type, Long orderNumber, List<BlockItemDto> blockItems) {
		super();
		this.id = id;
		this.type = type;
		this.orderNumber = orderNumber;
		this.blockItems = blockItems;
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

	public void setBlockItems(List<BlockItemDto> blockItems) {
		this.blockItems = blockItems;
	}

	public List<BlockItemDto> getBlockItems() {
		return blockItems;
	}
	
	@Override
	public int compareTo(ContentBlockDto o) {
		if (orderNumber == null || o.getOrderNumber() == null) {
		      return 0;
	    }
	    return orderNumber.compareTo(o.getOrderNumber());
	}

	@Override
	public String toString() {
		return "ContentBlockDto [id=" + id + ", type=" + type + ", orderNumber=" + orderNumber + ", blockItems="
				+ blockItems + "]";
	}
	
}
