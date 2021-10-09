package com.saitama.microservices.blogservice.resource;

public enum BlockType {
	
	PARAGRAPH("paragraph"),
	HEADING_ONE("heading-one"),
	HEADING_TWO("heading-two"),
	BLOCK_QUOTE("block-quote"),
	NUMBERED_LIST("numbered-list"),
	BULLETED_LIST("bulleted-list"),
	LINK("link"),
	IMAGE("image");
	
	private String type;
	
	BlockType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
