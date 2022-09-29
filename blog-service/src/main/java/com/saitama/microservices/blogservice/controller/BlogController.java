package com.saitama.microservices.blogservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.saitama.microservices.blogservice.dto.BlogPostDTO;
import com.saitama.microservices.blogservice.dto.TagDTO;
import com.saitama.microservices.blogservice.service.IBlogPostService;
import com.saitama.microservices.commonlib.dto.PageRequestDTO;
import com.saitama.microservices.commonlib.dto.PaginationDTO;

@RestController
@RequestMapping("/blog")
public class BlogController {
	
	private final IBlogPostService blogPostService;
	
	@Autowired
	public BlogController(IBlogPostService blogPostService) {
		this.blogPostService = blogPostService;
	}
	
	
	@GetMapping("/{id}")
	public BlogPostDTO getBlogPost(@PathVariable Long id) {
		return blogPostService.getById(id);
	}
	
	@GetMapping("/tag")
	public PaginationDTO<TagDTO> getBlogTags(@RequestBody(required = false) PageRequestDTO pageDto) {
		return blogPostService.getTags(pageDto);
	}
	
	@GetMapping("/{id}/tag")
	public TagDTO getBlogTag(@PathVariable Long id) {
		return blogPostService.getTagById(id);
	}
	
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public BlogPostDTO createBlogPost(@RequestBody BlogPostDTO postDto) {
		return blogPostService.create(postDto);
	}
	
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public BlogPostDTO updateBlogPost(@PathVariable Long id, @RequestBody BlogPostDTO postDto) {
		return blogPostService.update(id, postDto);
	}
	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteBlogPost(@PathVariable Long id) {
		blogPostService.delete(id);
	}
	
}
