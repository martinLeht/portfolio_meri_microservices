package com.saitama.microservices.blogservice.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.saitama.microservices.blogservice.resource.BlockItemType;

@Entity
@Table(name = "block_item")
public class BlockItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "item_type")
	@Enumerated(EnumType.STRING)
	private BlockItemType type;
	
	@Column(name ="order_number")
	private Long orderNumber;
	
	@Column(name ="file_name")
	private String fileName;
	
	@Column(name ="url_link", length = 1000)
	private String urlLink;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "blockItem", cascade = CascadeType.ALL)
	private List<TextFragment> textFragments = new ArrayList();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "block_id", nullable = false)
	private ContentBlock contentBlock;
	
	public BlockItem() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BlockItemType getType() {
		return type;
	}

	public void setType(BlockItemType type) {
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

	public List<TextFragment> getTextFragments() {
		return textFragments;
	}

	public void setTextFragments(List<TextFragment> textFragments) {
		this.textFragments = textFragments;
	}

	public ContentBlock getContentBlock() {
		return contentBlock;
	}

	public void setContentBlock(ContentBlock contentBlock) {
		this.contentBlock = contentBlock;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contentBlock == null) ? 0 : contentBlock.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orderNumber == null) ? 0 : orderNumber.hashCode());
		result = prime * result + ((textFragments == null) ? 0 : textFragments.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((urlLink == null) ? 0 : urlLink.hashCode());
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
		BlockItem other = (BlockItem) obj;
		if (contentBlock == null) {
			if (other.contentBlock != null)
				return false;
		} else if (!contentBlock.equals(other.contentBlock))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (orderNumber == null) {
			if (other.orderNumber != null)
				return false;
		} else if (!orderNumber.equals(other.orderNumber))
			return false;
		if (textFragments == null) {
			if (other.textFragments != null)
				return false;
		} else if (!textFragments.equals(other.textFragments))
			return false;
		if (type != other.type)
			return false;
		if (urlLink == null) {
			if (other.urlLink != null)
				return false;
		} else if (!urlLink.equals(other.urlLink))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BlockItem [id=" + id + ", type=" + type + ", orderNumber=" + orderNumber + ", fileName=" + fileName
				+ ", urlLink=" + urlLink + ", textFragments=" + textFragments + "]";
	}
	
}
