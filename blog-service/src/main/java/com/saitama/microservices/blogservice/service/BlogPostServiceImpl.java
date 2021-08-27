package com.saitama.microservices.blogservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saitama.microservices.blogservice.entity.BlockItem;
import com.saitama.microservices.blogservice.entity.BlogPost;
import com.saitama.microservices.blogservice.entity.ContentBlock;
import com.saitama.microservices.blogservice.entity.Tag;
import com.saitama.microservices.blogservice.entity.TextFragment;
import com.saitama.microservices.blogservice.repository.BlogPostRepository;
import com.saitama.microservices.blogservice.repository.TagRepository;
import com.saitama.microservices.blogservice.resource.BlockType;

@Service
public class BlogPostServiceImpl implements IBlogPostService {
	
	private static final int INTRO_LENGTH_LIMIT = 100;
	
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
	public Tag getTagById(Long id) {
		return tagRepository.getById(id);
	}
	
	
	@Override
	public BlogPost createBlogPost(BlogPost post) {
		StringBuilder postIntroSb = new StringBuilder();
		for (ContentBlock block: post.getContent()) {
			block.setPost(post);
			
			if (block.getType() == BlockType.PARAGRAPH) {
				for (BlockItem item: block.getBlockItems()) {
					item.setContentBlock(block);
					for (TextFragment fragment: item.getTextFragments()) {
						appendToIntro(postIntroSb, fragment.getText());
						fragment.setBlockItem(item);
					}
				}
			} else {
				for (BlockItem item: block.getBlockItems()) {
					item.setContentBlock(block);
					for (TextFragment fragment: item.getTextFragments()) {
						fragment.setBlockItem(item);
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
		
		Tag tag = new Tag();
		tag.setPostTitle(post.getTitle());
		tag.setPostIntro(postIntro);
		tag.setPost(post);
		
		post.setTag(tag);
		
		BlogPost newPost = blogPostRepository.save(post);
		
		return newPost;
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
			}
			
		}
	}

	@Override
	public void deleteBlogPostById(Long id) {
		blogPostRepository.deleteById(id);
	}

}
