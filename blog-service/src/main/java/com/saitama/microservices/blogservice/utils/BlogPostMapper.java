package com.saitama.microservices.blogservice.utils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.saitama.microservices.blogservice.dto.BlockItemDto;
import com.saitama.microservices.blogservice.dto.BlogPostDto;
import com.saitama.microservices.blogservice.dto.ContentBlockDto;
import com.saitama.microservices.blogservice.dto.TagDto;
import com.saitama.microservices.blogservice.dto.TextFragmentDto;
import com.saitama.microservices.blogservice.entity.BlockItem;
import com.saitama.microservices.blogservice.entity.BlogPost;
import com.saitama.microservices.blogservice.entity.ContentBlock;
import com.saitama.microservices.blogservice.entity.Tag;
import com.saitama.microservices.blogservice.entity.TextFragment;
import com.saitama.microservices.blogservice.resource.BlockItemType;
import com.saitama.microservices.blogservice.resource.BlockType;


public class BlogPostMapper {
	
	private final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd-M-yyyy hh:mm");

	public BlogPostDto convertBlogPostToDto(BlogPost post) {
		if (post == null) {
			return null;
		}
		
		BlogPostDto postDto = new BlogPostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setCreatedAt(dateTimeFormatter.format(post.getCreatedAt()));
		postDto.setUpdatedAt(dateTimeFormatter.format(post.getUpdatedAt()));
		
		List<ContentBlockDto> contentBlockDtos = post.getContent().stream()
				.map(this::convertContentBlockToDto)
				.collect(Collectors.toList());
		postDto.setContent(contentBlockDtos);
		
		return postDto;
	}
	
	
	public ContentBlockDto convertContentBlockToDto(ContentBlock contentBlock) {
		if (contentBlock == null) {
			return null;
		}
		
		ContentBlockDto contentBlockDto = new ContentBlockDto();
		contentBlockDto.setId(contentBlock.getId());
		contentBlockDto.setOrderNumber(contentBlock.getOrderNumber());
		contentBlockDto.setType(contentBlock.getType().getType());
		
		List<BlockItemDto> blockItemDtos = contentBlock.getBlockItems().stream()
				.map(this::convertBlockItemToDto)
				.collect(Collectors.toList());
		contentBlockDto.setBlockItems(blockItemDtos);
		
		return contentBlockDto;
	}
	
	public TagDto convertTagToDto(Tag tag) {
		if (tag == null) {
			return null;
		}
		
		TagDto tagDto = new TagDto();
		tagDto.setId(tag.getId());
		tagDto.setPostTitle(tag.getPostTitle());
		tagDto.setPostIntro(tag.getPostIntro());
		tagDto.setCreatedAt(dateTimeFormatter.format(tag.getCreatedAt()));
		
		return tagDto;
	}
	
	public BlockItemDto convertBlockItemToDto(BlockItem blockItem) {
		if (blockItem == null) {
			return null;
		}
		
		BlockItemDto blockItemDto = new BlockItemDto();
		blockItemDto.setId(blockItem.getId());
		blockItemDto.setType(blockItem.getType().getType());
		blockItemDto.setOrderNumber(blockItem.getOrderNumber());
		blockItemDto.setId(blockItem.getId());
		
		List<TextFragmentDto> textFragmentDtos = blockItem.getTextFragments().stream()
				.map(this::convertTextFragmentToDto)
				.collect(Collectors.toList());
		blockItemDto.setTextFragments(textFragmentDtos);
		
		return blockItemDto;
	}
	
	
	public TextFragmentDto convertTextFragmentToDto(TextFragment fragment) {
		if (fragment == null) {
			return null;
		}
		
		TextFragmentDto fragmentDto = new TextFragmentDto();
		fragmentDto.setId(fragment.getId());
		fragmentDto.setText(fragment.getText());
		fragmentDto.setBold(fragment.isBold());
		fragmentDto.setItalic(fragment.isItalic());
		fragmentDto.setUnderline(fragment.isUnderline());
		fragmentDto.setOrderNumber(fragment.getOrderNumber());
		
		return fragmentDto;
	}
	
	
	public BlogPost convertBlogPostDtoToEntity(BlogPostDto postDto) {
		if (postDto == null) {
			return null;
		}
		
		BlogPost post = new BlogPost();
		post.setId(postDto.getId());
		post.setTitle(postDto.getTitle());
		
		List<ContentBlock> contentBlocks = postDto.getContent().stream()
				.map(this::convertContentBlockDtoToEntity)
				.collect(Collectors.toList());
		post.setContent(contentBlocks);
		
		return post;
	}
	
	public Tag convertTagDtoToEntity(TagDto tagDto) {
		if (tagDto == null) {
			return null;
		}
		
		Tag tag = new Tag();
		tag.setId(tagDto.getId());
		tag.setPostTitle(tagDto.getPostTitle());
		tag.setPostIntro(tagDto.getPostIntro());
		
		return tag;
	}
	
	
	
	public ContentBlock convertContentBlockDtoToEntity(ContentBlockDto contentBlockDto) {
		if (contentBlockDto == null) {
			return null;
		}
		
		ContentBlock contentBlock = new ContentBlock();
		contentBlock.setId(contentBlockDto.getId());
		contentBlock.setOrderNumber(contentBlockDto.getOrderNumber());
		
		Optional<BlockType> optBlockType = resolveContentBlockType(contentBlockDto.getType());
		contentBlock.setType(optBlockType.orElseGet(() -> BlockType.PARAGRAPH));
		
		List<BlockItem> blockItems = contentBlockDto.getBlockItems().stream()
				.map(this::convertBlockItemDtoToEntity)
				.collect(Collectors.toList());
		contentBlock.setBlockItems(blockItems);
		
		return contentBlock;
	}
	
	public Optional<BlockType> resolveContentBlockType(String type) {
		for (BlockType blockType: BlockType.values()) {
			if (blockType.getType().equals(type)) {
				return Optional.of(blockType);
			}
		}
		return Optional.empty();
	}
	
	public BlockItem convertBlockItemDtoToEntity(BlockItemDto blockItemDto) {
		if (blockItemDto == null) {
			return null;
		}
		
		BlockItem blockItem = new BlockItem();
		blockItem.setId(blockItemDto.getId());
		blockItem.setOrderNumber(blockItemDto.getOrderNumber());
		blockItem.setId(blockItemDto.getId());
		
		Optional<BlockItemType> optItemType = resolveBlockItemType(blockItemDto.getType());
		blockItem.setType(optItemType.orElseGet(() -> BlockItemType.TEXT_ITEM));
		
		List<TextFragment> textFragments = blockItemDto.getTextFragments().stream()
				.map(this::convertTextFragmentDtoToEntity)
				.collect(Collectors.toList());
		blockItem.setTextFragments(textFragments);
		
		return blockItem;
	}
	
	public Optional<BlockItemType> resolveBlockItemType(String type) {
		for (BlockItemType blockItemType: BlockItemType.values()) {
			if (blockItemType.getType().equals(type)) {
				return Optional.of(blockItemType);
			}
		}
		return Optional.empty();
	}
	
	
	public TextFragment convertTextFragmentDtoToEntity(TextFragmentDto fragmentDto) {
		if (fragmentDto == null) {
			return null;
		}
		
		TextFragment fragment = new TextFragment();
		fragment.setId(fragmentDto.getId());
		fragment.setText(fragmentDto.getText());
		fragment.setBold(fragmentDto.isBold());
		fragment.setItalic(fragmentDto.isItalic());
		fragment.setUnderline(fragmentDto.isUnderline());
		fragment.setOrderNumber(fragmentDto.getOrderNumber());
		
		return fragment;
	}
	
	
}
