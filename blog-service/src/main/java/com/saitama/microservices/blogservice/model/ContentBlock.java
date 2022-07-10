package com.saitama.microservices.blogservice.model;

import java.util.List;

public class ContentBlock {
	
	private String type;
	
	private Attachment attachment;
	
	private List<TextFragment> textContent;
	
	private List<ContentBlock> childNodes;
	

	public ContentBlock() {}


	public ContentBlock(String type, Attachment attachment, List<TextFragment> textContent,
			List<ContentBlock> childNodes) {
		this.type = type;
		this.attachment = attachment;
		this.textContent = textContent;
		this.childNodes = childNodes;
	}

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	

	public Attachment getAttachment() {
		return attachment;
	}


	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	
	public List<TextFragment> getTextContent() {
		return textContent;
	}


	public void setTextContent(List<TextFragment> textContent) {
		this.textContent = textContent;
	}


	public List<ContentBlock> getChildNodes() {
		return childNodes;
	}


	public void setChildNodes(List<ContentBlock> childNodes) {
		this.childNodes = childNodes;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attachment == null) ? 0 : attachment.hashCode());
		result = prime * result + ((textContent == null) ? 0 : textContent.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ContentBlock other = (ContentBlock) obj;
		if (attachment == null) {
			if (other.attachment != null)
				return false;
		} else if (!attachment.equals(other.attachment))
			return false;
		if (textContent == null) {
			if (other.textContent != null)
				return false;
		} else if (!textContent.equals(other.textContent))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "ContentBlock [type=" + type + ", attachment=" + attachment + ", textContent=" + textContent + "]";
	}	
	
}
