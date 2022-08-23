package com.saitama.microservices.blogservice.service;

import java.util.List;

import com.saitama.microservices.blogservice.dto.BlogPostDTO;
import com.saitama.microservices.blogservice.dto.TagDTO;
import com.saitama.microservices.commonlib.dto.PageRequestDTO;
import com.saitama.microservices.commonlib.dto.PaginationDTO;


public interface IBlogPostService {
	
	public List<BlogPostDTO> getBlogPosts();
	
	public List<BlogPostDTO> getBlogPostsByUserId(String userId);
	
	public BlogPostDTO getBlogPostById(Long id);
	
	public PaginationDTO<TagDTO> getTags(PageRequestDTO pageDto);
	
	public List<TagDTO> getTagsByUserId(String userId);
	
	public List<TagDTO> getLatestTagsByUserId(String userId);
	
	public TagDTO getTagById(Long id);
	
	public BlogPostDTO createBlogPost(BlogPostDTO postDto);
	
	public BlogPostDTO updateBlogPost(Long id, BlogPostDTO postDto);
	
	public void deleteBlogPostById(Long id);
}
