package com.saitama.microservices.blogservice.dto;


public class TextFragmentDto {

	private Long id;
	
	private String text;
	
	private boolean bold;
	
	private boolean italic;
	
	private boolean underline;
	
	private Long orderNumber;
	
	/*
	@JsonIgnore
	private ContentBlockDto contentBlockDto;
	*/
	
	public TextFragmentDto() {}


	public TextFragmentDto(Long id, String text, boolean bold, boolean italic, boolean underline, Long orderNumber) {
		super();
		this.id = id;
		this.text = text;
		this.bold = bold;
		this.italic = italic;
		this.underline = underline;
		this.orderNumber = orderNumber;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public boolean isBold() {
		return bold;
	}


	public void setBold(boolean bold) {
		this.bold = bold;
	}


	public boolean isItalic() {
		return italic;
	}


	public void setItalic(boolean italic) {
		this.italic = italic;
	}


	public boolean isUnderline() {
		return underline;
	}


	public void setUnderline(boolean underline) {
		this.underline = underline;
	}


	public Long getOrderNumber() {
		return orderNumber;
	}


	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}


	@Override
	public String toString() {
		return "TextFragmentDto [id=" + id + ", text=" + text + ", bold=" + bold + ", italic=" + italic + ", underline="
				+ underline + ", orderNumber=" + orderNumber + "]";
	}

	
}
