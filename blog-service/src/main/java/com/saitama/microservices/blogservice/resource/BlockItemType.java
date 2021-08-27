package com.saitama.microservices.blogservice.resource;

public enum BlockItemType {
	
	TEXT_ITEM("text-item"),
	LIST_ITEM("list-item");
	
	private String type;
	
	BlockItemType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
