package com.saitama.microservices.blogservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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

import com.saitama.microservices.blogservice.dto.BlogPostDto;
import com.saitama.microservices.blogservice.dto.TagDto;
import com.saitama.microservices.blogservice.entity.BlogPost;
import com.saitama.microservices.blogservice.entity.Tag;
import com.saitama.microservices.blogservice.service.IBlogPostService;
import com.saitama.microservices.blogservice.utils.BlogPostMapper;

@RestController
@RequestMapping("/api")
public class BlogController {

	
	@Autowired
	private IBlogPostService blogPostService;
	
	@Autowired
	private BlogPostMapper blogPostMapper;
	
	
	@GetMapping("/blog")
	public List<BlogPostDto> getBlogPosts() {
		List<BlogPost> posts = blogPostService.getBlogPosts();
		List<BlogPostDto> postsDto = posts.stream()
				.map(blogPostMapper::convertBlogPostToDto)
				.collect(Collectors.toList());
		
		return postsDto;
	}
	
	
	@GetMapping("/blog/{id}")
	public BlogPostDto getBlogPost(@PathVariable Long id) {
		BlogPost post = blogPostService.getBlogPostById(id);
		
		return blogPostMapper.convertBlogPostToDto(post);
	}
	
	@GetMapping("/blog/tag")
	public List<TagDto> getBlogTags() {
		List<Tag> tags = blogPostService.getTags();
		List<TagDto> tagDtos = tags.stream()
				.map(blogPostMapper::convertTagToDto)
				.collect(Collectors.toList());
		
		return tagDtos;
	}
	
	@GetMapping("/blog/{id}/tag")
	public TagDto getBlogTag(@PathVariable Long id) {
		Tag tag = blogPostService.getTagById(id);
		
		return blogPostMapper.convertTagToDto(tag);
	}
	
	
	@PostMapping("/blog")
	@ResponseStatus(HttpStatus.CREATED)
	public BlogPostDto createBlogPost(@RequestBody BlogPostDto postDto) {
		System.out.println(postDto);
		
		BlogPost post = blogPostMapper.convertBlogPostDtoToEntity(postDto);
		BlogPost newPost = blogPostService.createBlogPost(post);
		
		return blogPostMapper.convertBlogPostToDto(newPost);
	}
	
	
	@PutMapping("/blog/{id}")
	@ResponseStatus(HttpStatus.OK)
	public BlogPostDto updateBlogPost(@PathVariable Long id, @RequestBody BlogPostDto postDto) {
		BlogPost post = blogPostMapper.convertBlogPostDtoToEntity(postDto);
		BlogPost updatedPost = blogPostService.updateBlogPost(id, post);
		return blogPostMapper.convertBlogPostToDto(updatedPost);
	}
	
	
	@DeleteMapping("/blog/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteBlogPost(@PathVariable Long id) {
		blogPostService.deleteBlogPostById(id);
	}
}
