package com.saitama.microservices.blogservice.resource;

public enum BlockItemType {
	
	TEXT_ITEM("text-item"),
	LINK_ITEM("link-item"),
	LIST_ITEM("list-item"),
	FILE_ITEM("file-item");
	
	private String type;
	
	BlockItemType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
