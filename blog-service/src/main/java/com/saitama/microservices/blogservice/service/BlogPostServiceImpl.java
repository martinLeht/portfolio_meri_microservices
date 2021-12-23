package com.saitama.microservices.blogservice.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saitama.microservices.blogservice.dto.BlockItemDto;
import com.saitama.microservices.blogservice.dto.BlogPostDto;
import com.saitama.microservices.blogservice.dto.ContentBlockDto;
import com.saitama.microservices.blogservice.dto.ImageInfoDto;
import com.saitama.microservices.blogservice.dto.TextFragmentDto;
import com.saitama.microservices.blogservice.entity.BlockItem;
import com.saitama.microservices.blogservice.entity.BlogPost;
import com.saitama.microservices.blogservice.entity.ChildEntity;
import com.saitama.microservices.blogservice.entity.ContentBlock;
import com.saitama.microservices.blogservice.entity.ParentEntity;
import com.saitama.microservices.blogservice.entity.Tag;
import com.saitama.microservices.blogservice.entity.TextFragment;
import com.saitama.microservices.blogservice.proxy.StorageServiceProxy;
import com.saitama.microservices.blogservice.repository.BlogPostRepository;
import com.saitama.microservices.blogservice.repository.ContentBlockRepository;
import com.saitama.microservices.blogservice.repository.ParentEntityRepository;
import com.saitama.microservices.blogservice.repository.TagRepository;
import com.saitama.microservices.blogservice.repository.TextFragmentRepository;
import com.saitama.microservices.blogservice.resource.BlockItemType;
import com.saitama.microservices.blogservice.resource.BlockType;
import com.saitama.microservices.blogservice.utils.BlogPostMapper;

@Service
public class BlogPostServiceImpl implements IBlogPostService {
	
	private static final int INTRO_LENGTH_LIMIT = 100;
	private static final String PLACEHOLDER_IMG = "image-placeholder.png";
	
	@Autowired
	private StorageServiceProxy storageServiceProxy;
	
	@Autowired
	private BlogPostRepository blogPostRepository;
	
	@Autowired
	private ParentEntityRepository parentEntityRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private ContentBlockRepository blockRepository;
	
	@Autowired
	private TextFragmentRepository fragmentRepository;
	
	@Autowired
	private BlogPostMapper blogPostMapper;

	@Override
	public List<BlogPost> getBlogPosts() {
		return blogPostRepository.findAll();
	}

	@Override
	public BlogPost getBlogPostById(Long id) {
		return blogPostRepository.getById(id);
	}

	
	@Override
	public List<Tag> getTags() {
		return tagRepository.findByOrderByCreatedAtDesc();
	}
	
	@Override
	public List<Tag> getLatestTags() {
		return tagRepository.findLatestTags();
	}


	@Override
	public Tag getTagById(Long id) {
		return tagRepository.getById(id);
	}
	
	
	@Override
	@Transactional
	public BlogPost createBlogPost(BlogPost post) {
		
		// Holds links to attachments
		List<String> attachments = new ArrayList<>();
		
		// Appends text fragments to construct post intro
		StringBuilder postIntroSb = new StringBuilder();
		
		for (ContentBlock block: post.getContent()) {
			if (block.getType() == BlockType.PARAGRAPH) {
				for (BlockItem item: block.getBlockItems()) {					
					for (TextFragment fragment: item.getTextFragments()) {
						appendTextToIntro(postIntroSb, fragment.getText());
					}
				}
			} else {
				for (BlockItem item: block.getBlockItems()) {
					if (item.getType() == BlockItemType.FILE_ITEM) {
						attachments.add(item.getUrlLink());
					}
				}
			}
			
		}
		
		System.out.println(post);
		String postIntro;
		if (postIntroSb.length() > INTRO_LENGTH_LIMIT) {
			postIntro = postIntroSb.substring(0, INTRO_LENGTH_LIMIT);
		} else {
			postIntro = postIntroSb.toString();
		}
		postIntro += "...";
		
		Tag tag = new Tag();
		tag.setPostTitle(post.getTitle());
		tag.setPostIntro(postIntro);
		tag.setPost(post);
		if (attachments.size() > 0) {
			tag.setThumbnail(attachments.get(0));
		} else{
			ImageInfoDto placeholderImg = storageServiceProxy.getFileUrl(PLACEHOLDER_IMG);
			tag.setThumbnail(placeholderImg.getUrl());
		}
		
		post.setTag(tag);
		BlogPost newPost = blogPostRepository.save(post);
		System.out.println(newPost);
		
		return newPost;
	}
	
	
	private String generatePostIntroForTag(BlogPost post) {
		StringBuilder postIntroSb = new StringBuilder();
		for (ContentBlock block: post.getContent()) {
			postIntroSb.append(" ");
			if (block.getType() == BlockType.PARAGRAPH) {
				for (BlockItem item: block.getBlockItems()) {					
					for (TextFragment fragment: item.getTextFragments()) {
						appendTextToIntro(postIntroSb, fragment.getText());
					}
				}
			}
		}
		
		String postIntro;
		if (postIntroSb.length() > INTRO_LENGTH_LIMIT) {
			postIntro = postIntroSb.substring(0, INTRO_LENGTH_LIMIT);
		} else {
			postIntro = postIntroSb.toString();
		}
		postIntro += "...";
		
		return postIntro;
	}
	

	private void appendTextToIntro(StringBuilder postIntroSb, String text) {
		if (postIntroSb.length() < INTRO_LENGTH_LIMIT ) {
			postIntroSb.append(text);
		}
	}
	
	@Override
	public BlogPost updateBlogPost(Long id, BlogPostDto postDto, BlogPost post) {
		BlogPost postToUpdate = blogPostRepository.getById(id);
		if (postToUpdate != null) {
			long startTime = System.nanoTime();
			
			List<String> attachments = new ArrayList<>();
			List<ContentBlock> newBlocks = new ArrayList<>();
			List<TextFragment> fragmentsToUpdate = new ArrayList<>();
			
			for (ContentBlockDto blockDto: postDto.getContent()) {
				if (blockDto.getId() == null) {
					ContentBlock block = blogPostMapper.convertContentBlockDtoToEntity(blockDto);
					block.setPost(postToUpdate);
					newBlocks.add(block);
					for (BlockItemDto itemDto: blockDto.getBlockItems()) {
						if (itemDto.getType().contentEquals(BlockItemType.FILE_ITEM.getType())) {
							attachments.add(itemDto.getUrlLink());
						}
					}
				} else {
					for (BlockItemDto itemDto: blockDto.getBlockItems()) {
						for (TextFragmentDto fragmentDto: itemDto.getTextFragments()) {
							Long fragId = fragmentDto.getId();
							if (fragId != null) {
								TextFragment fragmentToUpdate = fragmentRepository.getById(fragmentDto.getId());
								boolean fragmentHasChanges = false;
								
								if (!fragmentToUpdate.getText().equals(fragmentDto.getText())) {
									fragmentToUpdate.setText(fragmentDto.getText());
									fragmentHasChanges = true;
								}
								if (fragmentToUpdate.isBold() != fragmentDto.isBold()) {
									fragmentToUpdate.setBold(fragmentDto.isBold());
									fragmentHasChanges = true;
								}
								if (fragmentToUpdate.isItalic() != fragmentDto.isItalic()) {
									fragmentToUpdate.setItalic(fragmentDto.isItalic());
									fragmentHasChanges = true;
								}
								if (fragmentToUpdate.isUnderline() != fragmentDto.isUnderline()) {
									fragmentToUpdate.setUnderline(fragmentDto.isUnderline());
									fragmentHasChanges = true;
								}
								
								if (fragmentHasChanges) fragmentsToUpdate.add(fragmentToUpdate);
							}
						}
						if (itemDto.getType().contentEquals(BlockItemType.FILE_ITEM.getType())) {
							attachments.add(itemDto.getUrlLink());
						}
					}
				}	
			}
			
			// Save modified text fragments
			if (fragmentsToUpdate.size() > 0) {
				System.out.print("Saving edited fragments...");
				fragmentRepository.saveAllAndFlush(fragmentsToUpdate);
				/*
				for (TextFragment fragment : fragments) {
					System.out.println(fragment.toString());
				}*/
				//postToUpdate = blogPostRepository.getById(id);
			}
			
			// Add new content blocks to post
			if (newBlocks.size() > 0) {
				System.out.print("Saving new content blocks...");
				blockRepository.saveAllAndFlush(newBlocks);
				/*
				for (ContentBlock block: blocks) {
					System.out.println(block.toString());
				}*/
				/*
				for (ContentBlock newBlock : newBlocks) {
					postToUpdate.addContentBlock(newBlock);
				}
				*/
			}
			
			System.out.print("Fetching new post data");
			BlogPost updatedPost = blogPostRepository.getById(id);
			//System.out.print(updatedPost.toString());
			
			// Updating actual post data and related tag
			if (!postDto.getTitle().isBlank() && !updatedPost.getTitle().equals(postDto.getTitle())) {
				updatedPost.setTitle(postDto.getTitle());
			}
			
			Tag tag = updatedPost.getTag();
			if (!postDto.getTitle().isBlank() && !tag.getPostTitle().equals(postDto.getTitle())) {
				tag.setPostTitle(postDto.getTitle());
			}
			String postIntro = generatePostIntroForTag(updatedPost);
			if (!tag.getPostIntro().equals(postIntro)) {
				tag.setPostIntro(postIntro);
			}
			if (attachments.size() > 1) {
				tag.setThumbnail(attachments.get(0));
			} else {
				ImageInfoDto placeholderImg = storageServiceProxy.getFileUrl(PLACEHOLDER_IMG);
				tag.setThumbnail(placeholderImg.getUrl());
			}
			
			BlogPost saved = blogPostRepository.save(updatedPost);
			
			
			long endTime = System.nanoTime();
			long duration = (endTime - startTime);
			System.out.print("Execution time: ");
			System.out.print(duration);
			System.out.println("ns");
			return saved;
		}
		return null;
	}
	
	private void mapPostDataToUpdate(BlogPostDto postDto, BlogPost postToUpdate) {
		
		
	}

	@Override
	@Transactional
	public void deleteBlogPostById(Long id) {
		BlogPost post = blogPostRepository.getById(id);
		if (post != null) {
			// Gather all blocks that contain an attached file
			List<ContentBlock> fileBlocks = post.getContent()
					.stream()
					.filter(block -> block.getType() == BlockType.IMAGE)
					.collect(Collectors.toList());
				List<String> fileNames = new ArrayList<>();
			for (int i = 0; i < fileBlocks.size(); i++) {
				fileNames.add(fileBlocks.get(i).getBlockItems().get(0).getFileName());
			}
			// Remove all files attached to post from storage before deleting post
			if (fileNames.size() > 0) {
				storageServiceProxy.deleteFiles(fileNames);
			}
			
			System.out.println(post);
			System.out.println("----------DELETING POSTS--------");
			blogPostRepository.delete(post);
		}
	}

	@Override
	@Transactional
	public void deleteParent(Long id) {
		parentEntityRepository.deleteById(id);
	}

	@Override
	public ParentEntity getEntityById(Long id) {
		return parentEntityRepository.getById(id);
	}
	
	
	

}
