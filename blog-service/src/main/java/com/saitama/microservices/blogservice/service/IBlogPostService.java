package com.saitama.microservices.blogservice.service;

import java.util.List;

import com.saitama.microservices.blogservice.dto.BlogPostDto;
import com.saitama.microservices.blogservice.dto.PageRequestDto;
import com.saitama.microservices.blogservice.dto.PaginationDto;
import com.saitama.microservices.blogservice.dto.TagDto;


public interface IBlogPostService {
	
	public List<BlogPostDto> getBlogPosts();
	
	public List<BlogPostDto> getBlogPostsByUserId(String userId);
	
	public BlogPostDto getBlogPostById(Long id);
	
	public PaginationDto<TagDto> getTags(PageRequestDto pageDto);
	
	public List<TagDto> getTagsByUserId(String userId);
	
	public List<TagDto> getLatestTagsByUserId(String userId);
	
	public TagDto getTagById(Long id);
	
	public BlogPostDto createBlogPost(BlogPostDto postDto);
	
	public BlogPostDto updateBlogPost(Long id, BlogPostDto postDto);
	
	public void deleteBlogPostById(Long id);
}
