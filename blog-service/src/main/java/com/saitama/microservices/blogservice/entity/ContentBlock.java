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

import com.saitama.microservices.blogservice.resource.BlockType;


@Entity
@Table(name = "content_block")
public class ContentBlock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private BlockType type;
	
	@Column(name ="order_number")
	private Long orderNumber;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contentBlock", cascade = CascadeType.ALL)
	private List<BlockItem> blockItems = new ArrayList();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private BlogPost post;
	
	public ContentBlock() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BlockType getType() {
		return type;
	}

	public void setType(BlockType type) {
		this.type = type;
	}
	
	public void setType(String type) {
		BlockType typeEnum = BlockType.valueOf(type);
		if (typeEnum != null) {
			this.type = typeEnum;
		} else {
			this.type = BlockType.PARAGRAPH;
		}
		
	}
	
	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<BlockItem> getBlockItems() {
		return blockItems;
	}

	public void setBlockItems(List<BlockItem> blockItems) {
		this.blockItems = blockItems;
	}

	public BlogPost getPost() {
		return post;
	}

	public void setPost(BlogPost post) {
		this.post = post;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blockItems == null) ? 0 : blockItems.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orderNumber == null) ? 0 : orderNumber.hashCode());
		result = prime * result + ((post == null) ? 0 : post.hashCode());
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
		if (blockItems == null) {
			if (other.blockItems != null)
				return false;
		} else if (!blockItems.equals(other.blockItems))
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
		if (post == null) {
			if (other.post != null)
				return false;
		} else if (!post.equals(other.post))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ContentBlock [id=" + id + ", type=" + type + ", blockItems=" + blockItems + "]";
	}
	
}
