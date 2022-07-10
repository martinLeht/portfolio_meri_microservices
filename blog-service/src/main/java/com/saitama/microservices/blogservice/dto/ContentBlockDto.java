package com.saitama.microservices.blogservice.dto;

import java.util.List;


public class ContentBlockDto {
	
	private String type;
	
	private AttachmentDto attachment;
	
	private List<TextFragmentDto> textContent;
	
	private List<ContentBlockDto> childNodes;
	

	public ContentBlockDto() {}


	public ContentBlockDto(String type, AttachmentDto attachment, List<TextFragmentDto> textContent, List<ContentBlockDto> childNodes) {
		this.type = type;
		this.textContent = textContent;
		this.childNodes = childNodes;
		this.attachment = attachment;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	
	public AttachmentDto getAttachment() {
		return attachment;
	}

	
	public void setAttachment(AttachmentDto attachment) {
		this.attachment = attachment;
	}
	
	
	public List<TextFragmentDto> getTextContent() {
		return textContent;
	}


	public void setTextContent(List<TextFragmentDto> textContent) {
		this.textContent = textContent;
	}


	public List<ContentBlockDto> getChildNodes() {
		return childNodes;
	}


	public void setChildNodes(List<ContentBlockDto> childNodes) {
		this.childNodes = childNodes;
	}
		
	
}
