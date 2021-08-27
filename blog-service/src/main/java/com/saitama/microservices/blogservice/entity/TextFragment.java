package com.saitama.microservices.blogservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "text_fragment")
public class TextFragment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "text", length = 1000)
	private String text;
	
	@Column(name = "bold")
	private boolean bold;
	
	@Column(name = "italic")
	private boolean italic;
	
	@Column(name = "underline")
	private boolean underline;
	
	@Column(name ="order_number")
	private Long orderNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "block_item_id", nullable = false)
	private BlockItem blockItem;
	
	
	public TextFragment() {}


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
	
	
	public BlockItem getBlockItem() {
		return blockItem;
	}


	public void setBlockItem(BlockItem blockItem) {
		this.blockItem = blockItem;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (bold ? 1231 : 1237);
		result = prime * result + ((blockItem == null) ? 0 : blockItem.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (italic ? 1231 : 1237);
		result = prime * result + ((orderNumber == null) ? 0 : orderNumber.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + (underline ? 1231 : 1237);
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
		TextFragment other = (TextFragment) obj;
		if (bold != other.bold)
			return false;
		if (blockItem == null) {
			if (other.blockItem != null)
				return false;
		} else if (!blockItem.equals(other.blockItem))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (italic != other.italic)
			return false;
		if (orderNumber == null) {
			if (other.orderNumber != null)
				return false;
		} else if (!orderNumber.equals(other.orderNumber))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (underline != other.underline)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "TextFragment [id=" + id + ", text=" + text + ", bold=" + bold + ", italic=" + italic + ", underline="
				+ underline + "]";
	}
	
}
