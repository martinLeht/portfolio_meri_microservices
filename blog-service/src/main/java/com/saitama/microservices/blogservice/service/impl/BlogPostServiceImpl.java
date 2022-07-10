package com.saitama.microservices.blogservice.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saitama.microservices.blogservice.dto.AttachmentDto;
import com.saitama.microservices.blogservice.dto.BlogPostDto;
import com.saitama.microservices.blogservice.dto.ContentBlockDto;
import com.saitama.microservices.blogservice.dto.ImageInfoDto;
import com.saitama.microservices.blogservice.dto.PageRequestDto;
import com.saitama.microservices.blogservice.dto.PaginationDto;
import com.saitama.microservices.blogservice.dto.TagDto;
import com.saitama.microservices.blogservice.dto.TextFragmentDto;
import com.saitama.microservices.blogservice.model.Attachment;
import com.saitama.microservices.blogservice.model.BlogPost;
import com.saitama.microservices.blogservice.model.ContentBlock;
import com.saitama.microservices.blogservice.model.Tag;
import com.saitama.microservices.blogservice.model.TextFragment;
import com.saitama.microservices.blogservice.proxy.StorageServiceProxy;
import com.saitama.microservices.blogservice.query.MongoQueryHelper;
import com.saitama.microservices.blogservice.repository.BlogPostRepository;
import com.saitama.microservices.blogservice.repository.TagRepository;
import com.saitama.microservices.blogservice.resource.BlockType;
import com.saitama.microservices.blogservice.service.IBlogPostService;
import com.saitama.microservices.blogservice.service.ISequenceGeneratorService;

@Service
public class BlogPostServiceImpl implements IBlogPostService {
	
	private static final String PLACEHOLDER_IMG = "image-placeholder.png";
	
	private final MongoQueryHelper queryHelper;
	private final StorageServiceProxy storageServiceProxy;
	private final BlogPostRepository blogPostRepository;
	private final ISequenceGeneratorService sequenceGenService;
	private final TagRepository tagRepository;
	private final ObjectMapper mapper;
	
	
	@Autowired
	public BlogPostServiceImpl(MongoQueryHelper queryHelper, StorageServiceProxy storageServiceProxy, BlogPostRepository blogPostRepository,
			ISequenceGeneratorService sequenceGenService, TagRepository tagRepository, ObjectMapper mapper) {
		this.queryHelper = queryHelper;
		this.storageServiceProxy = storageServiceProxy;
		this.blogPostRepository = blogPostRepository;
		this.sequenceGenService = sequenceGenService;
		this.tagRepository = tagRepository;
		this.mapper = mapper;
	}

	@Override
	public List<BlogPostDto> getBlogPosts() {
		List<BlogPost> posts = blogPostRepository.findAll();
		List<BlogPostDto> postsDto = posts.stream()
				.map(this::convertBlogPostToDto)
				.collect(Collectors.toList());
		return postsDto;
	}
	
	@Override
	public List<BlogPostDto> getBlogPostsByUserId(String userId) {
		List<BlogPost> posts = queryHelper.queryByField("userId", userId, BlogPost.class);
		List<BlogPostDto> postsDto = posts.stream()
				.map(this::convertBlogPostToDto)
				.collect(Collectors.toList());
		return postsDto;
	}

	@Override
	public BlogPostDto getBlogPostById(Long id) {
		Optional<BlogPost> blogPostOpt = blogPostRepository.findById(id);
		if (blogPostOpt.isPresent()) {
			BlogPost blogPost = blogPostOpt.get();
			/*
			List<String> expiredAttachmentNames = blogPost.getAttachments().stream()
										.filter(att -> hasAttachmentUrlExpired(att.getLink()))
										.map(att -> att.getName())
										.distinct()
										.collect(Collectors.toList());
			if (expiredAttachmentNames.size() > 0) {
				Map<String, ImageInfoDto> refreshedAttachmentsDto = storageServiceProxy.getFilesUrls(expiredAttachmentNames);
				
				// Replace the expired attachment URL:s in post content
				for (ContentBlock block: blogPost.getContent()) {
					if (block.getType().equals(BlockType.IMAGE.getType())) {
						String attachmentName = block.getAttachment().getName();
						if (refreshedAttachmentsDto.containsKey(attachmentName)) {
							block.getAttachment().setLink(refreshedAttachmentsDto.get(attachmentName).getUrl());
						}
					}
				}
				
				if (blogPost.isNew()) blogPost.setPersisted(true);
				
				BlogPost updatedPost = blogPostRepository.save(blogPost);
				
				return convertBlogPostToDto(updatedPost);
			}
			*/
			
			return convertBlogPostToDto(blogPost);
		}
		return null;
	}

	
	@Override
	public PaginationDto<TagDto> getTags(PageRequestDto pageDto) {
		Page<Tag> tags = null;
		Pageable sortedByCreatedAt = null;
		if (pageDto == null || pageDto.getPage() == null || pageDto.getSize() == null) {
			sortedByCreatedAt = PageRequest.of(PageRequestDto.DEFAULT_PAGE, PageRequestDto.DEFAULT_SIZE, Sort.Direction.DESC, "createdAt");
		} else {
			if (pageDto.getSize() > PageRequestDto.MAX_SIZE) {
				sortedByCreatedAt = PageRequest.of(pageDto.getPage(), PageRequestDto.MAX_SIZE, Sort.Direction.DESC, "createdAt");
			} else {
				sortedByCreatedAt = PageRequest.of(pageDto.getPage(), pageDto.getSize(), Sort.Direction.DESC, "createdAt");
			}
		}
		tags = tagRepository.findAll(sortedByCreatedAt);		
		
		List<TagDto> tagDtos = tags.stream()
				.map(this::convertTagToDto)
				.collect(Collectors.toList());
		
		if (tags.getNumber() + 1 < tags.getTotalPages()) {
			return new PaginationDto<TagDto>(tags.getNumber(), tags.getSize(), tags.getTotalElements(), tags.getNumber() + 1, tagDtos);
		} else {
			return new PaginationDto<TagDto>(tags.getNumber(), tags.getSize(), tags.getTotalElements(), null, tagDtos);
		}
	}
	
	@Override
	public List<TagDto> getTagsByUserId(String userId) {
		List<Tag> tags = queryHelper.queryByFieldAndSort("userId", userId, Sort.Direction.DESC, "createdAt", Tag.class);
		List<TagDto> tagsDto = tags.stream()
				.map(this::convertTagToDto)
				.collect(Collectors.toList());
		List<TagDto> tagDtos = tags.stream()
				.map(this::convertTagToDto)
				.collect(Collectors.toList());
		
		return tagDtos;
	}
	
	@Override
	public List<TagDto> getLatestTagsByUserId(String userId) {
		List<Tag> tags = queryHelper.queryByFieldAndSort("userId", userId, Sort.Direction.DESC, "createdAt", Tag.class);
		List<TagDto> tagDtos = tags.stream()
				.map(this::convertTagToDto)
				.collect(Collectors.toList());
		return tagDtos;
	}


	@Override
	public TagDto getTagById(Long id) {
		Optional<Tag> tagOpt = tagRepository.findById(id);
		if (tagOpt.isPresent()) {
			return convertTagToDto(tagOpt.get());
		}
		return null;
	}
	
	
	@Override
	public BlogPostDto createBlogPost(BlogPostDto postDto) {
		BlogPost post = mapper.convertValue(postDto, BlogPost.class);
		
		// Appends text fragments to construct post intro
		StringBuilder postIntroSb = new StringBuilder();
		
		for (ContentBlock block: post.getContent()) {
			if (block.getType().equals(BlockType.PARAGRAPH.getType())) {
				for (TextFragment textFrag: block.getTextContent()) {
					postIntroSb.append(" ");
					postIntroSb.append(textFrag.getText());
				}
			}
		}
		
		post.setId(sequenceGenService.generateSequence(BlogPost.ID_SEQUENCE));
		
		BlogPost newPost = blogPostRepository.save(post);
		
		Tag tag = new Tag();
		tag.setId(newPost.getId());
		tag.setPostId(newPost.getId());
		tag.setPostTitle(post.getTitle());
		tag.setPostIntro(postIntroSb.toString());
		tag.setUserId(newPost.getUserId());
		
		if (newPost.getAttachments().size() > 0) {
			Attachment thumbnail = newPost.getAttachments().get(0);
			tag.setThumbnail(thumbnail);
		} else {
			ImageInfoDto placeholderImg = storageServiceProxy.getFileUrl(PLACEHOLDER_IMG);
			tag.setThumbnail(new Attachment(placeholderImg.getName(), placeholderImg.getUrl()));
		}
		Tag newTag = tagRepository.save(tag);
		
		if (newTag != null) {
			TagDto tagDto = convertTagToDto(newTag);
			BlogPostDto newPostDto = convertBlogPostToDto(newPost);
			newPostDto.setTag(tagDto);
			return newPostDto;
		}
		
		return null;
	}
	
	@Override
	public BlogPostDto updateBlogPost(Long id, BlogPostDto postDto) {
		Optional<BlogPost> postToUpdateOpt = blogPostRepository.findById(id);
		if (postToUpdateOpt.isPresent()) {
			BlogPost postToUpdate = postToUpdateOpt.get();
			postToUpdate.setPersisted(true);
			postToUpdate.setTitle(postDto.getTitle());
			
			List<ContentBlock> content = postDto.getContent().stream()
					.map(contentBlockDto -> mapper.convertValue(contentBlockDto, ContentBlock.class))
					.collect(Collectors.toList());
			List<Attachment> attachments = postDto.getAttachments().stream()
					.map(attachmentDto -> convertAttachmentDtoToModel(attachmentDto))
					.collect(Collectors.toList());
			postToUpdate.setContent(content);
			postToUpdate.setAttachments(attachments);
			
			BlogPost saved = blogPostRepository.save(postToUpdate);
			
			if (saved != null) {
				
				Optional<Tag> tagOpt = tagRepository.findById(saved.getId());
				if (tagOpt.isPresent()) {
					// Appends text fragments to construct post intro
					StringBuilder postIntroSb = new StringBuilder();
					for (ContentBlock block: saved.getContent()) {
						if (block.getType().equals(BlockType.PARAGRAPH.getType())) {
							for (TextFragment textFrag: block.getTextContent()) {
								postIntroSb.append(" ");
								postIntroSb.append(textFrag.getText());
							}
						}
					}
					
					Tag tag = tagOpt.get();
					tag.setPersisted(true);
					tag.setPostTitle(saved.getTitle());
					tag.setPostIntro(postIntroSb.toString());
					
					if (saved.getAttachments().size() > 0) {
						tag.setThumbnail(saved.getAttachments().get(0));			
					} else {
						ImageInfoDto placeholderImg = storageServiceProxy.getFileUrl(PLACEHOLDER_IMG);
						tag.setThumbnail(new Attachment(placeholderImg.getName(), placeholderImg.getUrl()));
					}
					
					
					Tag savedTag = tagRepository.save(tag);
					TagDto tagDto = convertTagToDto(savedTag);
					BlogPostDto newPostDto = convertBlogPostToDto(saved);
					newPostDto.setTag(tagDto);
					return newPostDto;
				}
			}
		}
		
		return null;
	}

	@Override
	public void deleteBlogPostById(Long id) {
		Optional<BlogPost> postOpt = blogPostRepository.findById(id);
		if (postOpt.isPresent()) {
			BlogPost post = postOpt.get();
			// Gather all blocks that contain an attached file
			List<ContentBlock> attachmentBlocks = post.getContent()
					.stream()
					.filter(block -> block.getType().equals(BlockType.IMAGE.getType()))
					.collect(Collectors.toList());
			List<String> fileNames = new ArrayList<>();
			for (int i = 0; i < attachmentBlocks.size(); i++) {
				fileNames.add(attachmentBlocks.get(i).getAttachment().getName());
			}
			// Remove all files attached to post from storage before deleting post
			if (fileNames.size() > 0) {
				storageServiceProxy.deleteFiles(fileNames);
			}
			
			blogPostRepository.delete(post);
			Optional<Tag> tagOpt = tagRepository.findById(id);
			tagOpt.ifPresent(tag -> tagRepository.delete(tag));
		}
	}
	
	/*
	private boolean hasAttachmentUrlExpired(String url) {
		String[] urlQueryParams = url.split("\\?(?!\\?)", 2)[1].split("&");
		Long expiryTimeEpoch = Arrays.stream(urlQueryParams)
										.filter(qParam -> qParam.startsWith("Expires"))
										.map(qParam -> Long.parseLong(qParam.split("=", 2)[1]))
										.findAny()
										.orElse(null);
		if (expiryTimeEpoch != null) {
			LocalDateTime expiryDateTime = LocalDateTime.ofEpochSecond(expiryTimeEpoch, 0, ZoneOffset.UTC);
			System.out.println(expiryDateTime);
			System.out.println(LocalDateTime.now().plusHours(2));
			return expiryDateTime.isAfter(LocalDateTime.now().plusHours(2));
		}
		
		return true;
	}
	*/
	
	private BlogPostDto convertBlogPostToDto(BlogPost post) {
		return mapper.convertValue(post, BlogPostDto.class);
	}
	
	private TagDto convertTagToDto(Tag tag) {
		return mapper.convertValue(tag, TagDto.class);
	}
	
	private Attachment convertAttachmentDtoToModel(AttachmentDto attachment) {
		return mapper.convertValue(attachment, Attachment.class);
	}
	

}
