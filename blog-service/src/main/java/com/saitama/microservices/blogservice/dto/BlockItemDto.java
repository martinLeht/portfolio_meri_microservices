package com.saitama.microservices.blogservice.dto;

import java.util.List;

public class BlockItemDto {
	
	private Long id;
	
	private String type;
	
	private Long orderNumber;
	
	private String fileName;
	
	private String urlLink;
	
	private List<TextFragmentDto> textFragments;
	
	public BlockItemDto() {}
	
	public BlockItemDto(Long id, String type, Long orderNumber, String fileName,String urlLink, List<TextFragmentDto> textFragments) {
		super();
		this.id = id;
		this.type = type;
		this.orderNumber = orderNumber;
		this.fileName = fileName;
		this.urlLink = urlLink;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUrlLink() {
		return urlLink;
	}

	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}

	public List<TextFragmentDto> getTextFragments() {
		return textFragments;
	}

	public void setTextFragments(List<TextFragmentDto> textFragments) {
		this.textFragments = textFragments;
	}

	@Override
	public String toString() {
		return "BlockItemDto [id=" + id + ", type=" + type + ", orderNumber=" + orderNumber + ", fileName=" + fileName
				+ ", urlLink=" + urlLink + ", textFragments=" + textFragments + "]";
	}
	
}
