package com.saitama.microservices.blogservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saitama.microservices.blogservice.entity.BlockItem;
import com.saitama.microservices.blogservice.entity.BlogPost;
import com.saitama.microservices.blogservice.entity.ContentBlock;
import com.saitama.microservices.blogservice.entity.Tag;
import com.saitama.microservices.blogservice.entity.TextFragment;
import com.saitama.microservices.blogservice.proxy.StorageServiceProxy;
import com.saitama.microservices.blogservice.repository.BlogPostRepository;
import com.saitama.microservices.blogservice.repository.TagRepository;
import com.saitama.microservices.blogservice.resource.BlockItemType;
import com.saitama.microservices.blogservice.resource.BlockType;

@Service
@Transactional(readOnly = true)
public class BlogPostServiceImpl implements IBlogPostService {
	
	private static final int INTRO_LENGTH_LIMIT = 100;
	
	@Autowired
	private StorageServiceProxy storageServiceProxy;
	
	@Autowired
	private BlogPostRepository blogPostRepository;
	
	@Autowired
	private TagRepository tagRepository;

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
		return tagRepository.findAll();
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
		List<String> attachments = new ArrayList<>(); 
		for (ContentBlock block: post.getContent()) {
			block.setPost(post);
			for (BlockItem item: block.getBlockItems()) {
				item.setContentBlock(block);
				for (TextFragment fragment: item.getTextFragments()) {
					fragment.setBlockItem(item);
				}
				if (item.getType() == BlockItemType.FILE_ITEM) {
					attachments.add(item.getUrlLink());
				}
			}
			
		}
		
		String postIntro = generatePostIntroForTag(post);
		
		Tag tag = new Tag();
		tag.setPostTitle(post.getTitle());
		tag.setPostIntro(postIntro);
		tag.setPost(post);
		if (attachments.size() > 0) {
			tag.setThumbnail(attachments.get(0));
		}
		
		post.setTag(tag);
		
		BlogPost newPost = blogPostRepository.save(post);
		
		return newPost;
	}
	
	private String generatePostIntroForTag(BlogPost post) {
		StringBuilder postIntroSb = new StringBuilder();
		for (ContentBlock block: post.getContent()) {
			postIntroSb.append(" ");
			if (block.getType() == BlockType.PARAGRAPH) {
				for (BlockItem item: block.getBlockItems()) {					
					for (TextFragment fragment: item.getTextFragments()) {
						appendToIntro(postIntroSb, fragment.getText());
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

	private void appendToIntro(StringBuilder postIntroSb, String text) {
		if (postIntroSb.length() < INTRO_LENGTH_LIMIT ) {
			postIntroSb.append(text);
		}
	}
	
	@Override
	public BlogPost updateBlogPost(Long id, BlogPost post) {
		BlogPost postToUpdate = blogPostRepository.getById(id);
		if (postToUpdate != null) {
			mapPostDataToUpdate(post, postToUpdate);
			BlogPost saved = blogPostRepository.save(postToUpdate);
			return saved;
		}
		return null;
	}
	
	private void mapPostDataToUpdate(BlogPost post, BlogPost postToUpdate) {
		postToUpdate.setTitle(post.getTitle());
		postToUpdate.setContent(post.getContent());
		
		List<String> attachments = new ArrayList<>(); 
		
		for (ContentBlock block: postToUpdate.getContent()) {
			if (block.getPost() == null) {
				block.setPost(postToUpdate);
			}
			for (BlockItem item: block.getBlockItems()) {
				if (item.getContentBlock() == null) {
					item.setContentBlock(block);
					
					for (TextFragment fragment: item.getTextFragments()) {
						if (fragment.getBlockItem() == null) {
							fragment.setBlockItem(item);
						}
					}
				}
				if (item.getType() == BlockItemType.FILE_ITEM) {
					attachments.add(item.getUrlLink());
				}
			}
		}
		
		Tag tag = postToUpdate.getTag();
		if (!tag.getPostTitle().equals(post.getTitle())) {
			tag.setPostTitle(post.getTitle());
		}
		String postIntro = generatePostIntroForTag(post);
		if (!tag.getPostIntro().equals(postIntro)) {
			tag.setPostIntro(postIntro);
		}
		if (attachments.size() > 1) {
			tag.setThumbnail(attachments.get(0));
		}
	}

	@Override
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
			
			blogPostRepository.deleteById(id);
		}
	}
	

}
