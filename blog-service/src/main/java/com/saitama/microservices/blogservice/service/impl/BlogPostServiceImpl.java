package com.saitama.microservices.blogservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saitama.microservices.blogservice.dto.AttachmentDTO;
import com.saitama.microservices.blogservice.dto.BlogPostDTO;
import com.saitama.microservices.blogservice.dto.TagDTO;
import com.saitama.microservices.blogservice.mapper.TagMapper;
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
import com.saitama.microservices.commonlib.constant.MediaCategory;
import com.saitama.microservices.commonlib.dto.MediaFileDTO;
import com.saitama.microservices.commonlib.dto.MediaListQueryDTO;
import com.saitama.microservices.commonlib.dto.MediaQueryDTO;
import com.saitama.microservices.commonlib.dto.PageRequestDTO;
import com.saitama.microservices.commonlib.dto.PaginationDTO;
import com.saitama.microservices.commonlib.dto.SearchRequestDTO;
import com.saitama.microservices.commonlib.exception.EntityNotFoundException;
import com.saitama.microservices.commonlib.util.UuidUtils;

@Service
public class BlogPostServiceImpl implements IBlogPostService {
	
	private static final String PLACEHOLDER_IMG = "image-placeholder.png";
	
	private final MongoQueryHelper queryHelper;
	private final StorageServiceProxy storageServiceProxy;
	private final BlogPostRepository blogPostRepository;
	private final TagRepository tagRepository;
	private final TagMapper tagMapper;
	private final ObjectMapper mapper;
	
	
	@Autowired
	public BlogPostServiceImpl(MongoQueryHelper queryHelper, StorageServiceProxy storageServiceProxy, BlogPostRepository blogPostRepository,
			TagRepository tagRepository, TagMapper tagMapper, ObjectMapper mapper) {
		this.queryHelper = queryHelper;
		this.storageServiceProxy = storageServiceProxy;
		this.blogPostRepository = blogPostRepository;
		this.tagRepository = tagRepository;
		this.tagMapper = tagMapper;
		this.mapper = mapper;
	}
	
	@Override
	public List<BlogPostDTO> getBlogPostsByUserId(String userId) {
		List<BlogPost> posts = queryHelper.queryByField("userId", userId, BlogPost.class);
		List<BlogPostDTO> postsDto = posts.stream()
				.map(this::convertBlogPostToDto)
				.collect(Collectors.toList());
		return postsDto;
	}

	@Override
	public BlogPostDTO getById(UUID id) {
		BlogPost blogPost = blogPostRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("blog-post-not-found", "Blog post not found with id: " + id));	
		return convertBlogPostToDto(blogPost);
	}

	
	@Override
	public PaginationDTO<TagDTO> getTags(PageRequestDTO pageDto) {
		Page<Tag> tags = null;
		Pageable sortedByCreatedAt = null;
		if (pageDto == null || pageDto.getPage() == null || pageDto.getSize() == null) {
			sortedByCreatedAt = PageRequest.of(PageRequestDTO.DEFAULT_PAGE, PageRequestDTO.DEFAULT_SIZE, Sort.Direction.DESC, "createdAt");
		} else {
			if (pageDto.getSize() > PageRequestDTO.MAX_SIZE) {
				sortedByCreatedAt = PageRequest.of(pageDto.getPage(), PageRequestDTO.MAX_SIZE, Sort.Direction.DESC, "createdAt");
			} else {
				sortedByCreatedAt = PageRequest.of(pageDto.getPage(), pageDto.getSize(), Sort.Direction.DESC, "createdAt");
			}
		}
		tags = tagRepository.findAll(sortedByCreatedAt);		
		
		return mapEntityPageToDTO(tags);
		
	}
	
	@Override
	public PaginationDTO<TagDTO> searchTags(SearchRequestDTO searchDto) {
		Pageable sortedByCreatedAt = null;
		if (searchDto == null || searchDto.getPage() == null || searchDto.getSize() == null) {
			sortedByCreatedAt = PageRequest.of(PageRequestDTO.DEFAULT_PAGE, PageRequestDTO.DEFAULT_SIZE, Sort.Direction.DESC, "createdAt");
		} else {
			if (searchDto.getSize() > PageRequestDTO.MAX_SIZE) {
				sortedByCreatedAt = PageRequest.of(searchDto.getPage(), PageRequestDTO.MAX_SIZE, Sort.Direction.DESC, "createdAt");
			} else {
				sortedByCreatedAt = PageRequest.of(searchDto.getPage(), searchDto.getSize(), Sort.Direction.DESC, "createdAt");
			}
		}
		
		if (searchDto.getSearchTerm() != null && !searchDto.getSearchTerm().isBlank()) {
			Page<Tag> tags = tagRepository.findByContentFlatContainingIgnoreCaseOrPostTitleContainingIgnoreCase(searchDto.getSearchTerm(), searchDto.getSearchTerm(), sortedByCreatedAt);
			return mapEntityPageToDTO(tags);
		} else {
			Page<Tag> tags = tagRepository.findAll(sortedByCreatedAt);
			return mapEntityPageToDTO(tags);
		}
	}
	
	private PaginationDTO<TagDTO> mapEntityPageToDTO(Page<Tag> page) {
		List<TagDTO> dtos = page.stream()
				.map(tagMapper::toDto)
				.collect(Collectors.toList());
		if (page.getNumber() + 1 < page.getTotalPages()) {
			return PaginationDTO.<TagDTO>builder()
					.page(page.getNumber())
					.pageSize(page.getSize())
					.totalSize(page.getTotalElements())
					.nextPage(page.getNumber() + 1)
					.data(dtos)
					.build();
		} else {
			return PaginationDTO.<TagDTO>builder()
					.page(page.getNumber())
					.pageSize(page.getSize())
					.totalSize(page.getTotalElements())
					.data(dtos)
					.build();
		}
	}
	
	@Override
	public BlogPostDTO create(BlogPostDTO postDto) {
		BlogPost post = mapper.convertValue(postDto, BlogPost.class);
		
		// Appends text fragments to construct post intro
		StringBuilder postIntroSb = new StringBuilder();
		StringBuilder postContentFlatSb = new StringBuilder();
		
		for (ContentBlock block: post.getContent()) {
			if (block.getType().equals(BlockType.PARAGRAPH.getType())) {
				for (TextFragment textFrag: block.getTextContent()) {
					if (textFrag.getText() != null && !textFrag.getText().isBlank()) {
						postIntroSb.append(" ");
						postIntroSb.append(textFrag.getText().strip());
						postContentFlatSb.append(" ");
						postContentFlatSb.append(textFrag.getText().strip());
					}
				}
			} else if (block.getType().equals(BlockType.HEADING_ONE.getType()) 
					|| block.getType().equals(BlockType.HEADING_TWO.getType()) 
					|| block.getType().equals(BlockType.BLOCK_QUOTE.getType())
					|| block.getType().equals(BlockType.LINK.getType())) {
				for (TextFragment textFrag: block.getTextContent()) {
					if (textFrag.getText() != null && !textFrag.getText().isBlank()) {
						postContentFlatSb.append(" ");
						postContentFlatSb.append(textFrag.getText().strip());
					}
				}
			} else if (block.getType().equals(BlockType.NUMBERED_LIST.getType()) || block.getType().equals(BlockType.BULLETED_LIST.getType())) {
				String listBlockAsString = block.getChildNodes().stream()
					.map(child -> child.getTextContent().stream()
							.filter(textFrag -> textFrag.getText() != null && !textFrag.getText().isBlank())
							.map(TextFragment::getText)
							.map(String::strip)
							.collect(Collectors.joining(" ")))
					.collect(Collectors.joining(" "));
				postContentFlatSb.append(" ");
				postContentFlatSb.append(listBlockAsString);
			}
		}
		
		BlogPost newPost = blogPostRepository.save(post);
		
		Tag tag = new Tag();
		tag.setId(newPost.getId());
		tag.setPostTitle(post.getTitle());
		tag.setPostIntro(postIntroSb.toString().strip());
		tag.setContentFlat(postContentFlatSb.toString().strip());
		tag.setUserId(newPost.getUserId());
		
		if (newPost.getAttachments().size() > 0) {
			Attachment thumbnail = newPost.getAttachments().get(0);
			tag.setThumbnail(thumbnail);
		} else {
			MediaFileDTO placeholderImg = storageServiceProxy.getFileUrl(
					MediaQueryDTO.builder()
						.fileName(PLACEHOLDER_IMG)
						.mediaCategory(MediaCategory.BLOG)
						.build());
			tag.setThumbnail(new Attachment(placeholderImg.getName(), placeholderImg.getSrc()));
		}
		Tag newTag = tagRepository.save(tag);
		
		if (newTag != null) {
			TagDTO tagDto = convertTagToDto(newTag);
			BlogPostDTO newPostDto = convertBlogPostToDto(newPost);
			newPostDto.setTag(tagDto);
			return newPostDto;
		}
		
		return null;
	}
	
	
	@Override
	public BlogPostDTO update(UUID id, BlogPostDTO postDto) {
		BlogPost postToUpdate = blogPostRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("blog-post-not-found", "Blog post not found with id: " + id));
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
		
		Tag tag = tagRepository.findById(saved.getId())
				.orElseThrow(() -> new EntityNotFoundException("tag-not-found", "Tag not found with id: " + saved.getId()));
		
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
		
		tag.setPostTitle(saved.getTitle());
		tag.setPostIntro(postIntroSb.toString());
		
		if (saved.getAttachments().size() > 0) {
			tag.setThumbnail(saved.getAttachments().get(0));			
		} else {
			MediaFileDTO placeholderImg = storageServiceProxy.getFileUrl(
					MediaQueryDTO.builder()
						.fileName(PLACEHOLDER_IMG)
						.mediaCategory(MediaCategory.BLOG)
						.build());
			tag.setThumbnail(new Attachment(placeholderImg.getName(), placeholderImg.getSrc()));
		}
		
		
		Tag savedTag = tagRepository.save(tag);
		TagDTO tagDto = convertTagToDto(savedTag);
		BlogPostDTO newPostDto = convertBlogPostToDto(saved);
		newPostDto.setTag(tagDto);
		return newPostDto;
	}

	@Override
	public void delete(UUID id) {
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
				storageServiceProxy.deleteFiles(
						MediaListQueryDTO.builder()
							.fileNames(fileNames)
							.mediaCategory(MediaCategory.BLOG)
							.build());
			}
			
			blogPostRepository.delete(post);
			Optional<Tag> tagOpt = tagRepository.findById(id);
			tagOpt.ifPresent(tag -> tagRepository.delete(tag));
		}
	}
	
	
	/**
	 * Only generated because of IBaseReadWriteService
	 */
	@Override
	public PaginationDTO<BlogPostDTO> getPaginated(PageRequestDTO pageDto) {
		return null;
	}
	
	private BlogPostDTO convertBlogPostToDto(BlogPost post) {
		return mapper.convertValue(post, BlogPostDTO.class);
	}
	
	private TagDTO convertTagToDto(Tag tag) {
		return mapper.convertValue(tag, TagDTO.class);
	}
	
	private Attachment convertAttachmentDtoToModel(AttachmentDTO attachment) {
		return mapper.convertValue(attachment, Attachment.class);
	}

}
