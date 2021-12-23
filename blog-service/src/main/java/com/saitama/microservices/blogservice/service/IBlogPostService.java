package com.saitama.microservices.blogservice.service;

import java.util.List;

import com.saitama.microservices.blogservice.dto.BlogPostDto;
import com.saitama.microservices.blogservice.entity.BlogPost;
import com.saitama.microservices.blogservice.entity.ParentEntity;
import com.saitama.microservices.blogservice.entity.Tag;

public interface IBlogPostService {
	
	public List<BlogPost> getBlogPosts();
	
	public BlogPost getBlogPostById(Long id);
	
	public List<Tag> getTags();
	
	public List<Tag> getLatestTags();
	
	public Tag getTagById(Long id);
	
	public BlogPost createBlogPost(BlogPost post);
	
	public BlogPost updateBlogPost(Long id, BlogPostDto postDto, BlogPost post);
	
	public void deleteBlogPostById(Long id);
	
	public void deleteParent(Long id);
	
	public ParentEntity getEntityById(Long id);
}
