package com.saitama.microservices.blogservice.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
import com.saitama.microservices.blogservice.dto.ChildDto;
import com.saitama.microservices.blogservice.dto.ParentDto;
import com.saitama.microservices.blogservice.dto.TagDto;
import com.saitama.microservices.blogservice.entity.BlogPost;
import com.saitama.microservices.blogservice.entity.ChildEntity;
import com.saitama.microservices.blogservice.entity.ParentEntity;
import com.saitama.microservices.blogservice.entity.Tag;
import com.saitama.microservices.blogservice.service.IBlogPostService;
import com.saitama.microservices.blogservice.utils.BlogPostMapper;

@RestController
@RequestMapping("/blog")
public class BlogController {

	
	@Autowired
	private IBlogPostService blogPostService;
	
	@Autowired
	private BlogPostMapper blogPostMapper;
	
	
	@GetMapping("/")
	public List<BlogPostDto> getBlogPosts() {
		List<BlogPost> posts = blogPostService.getBlogPosts();
		List<BlogPostDto> postsDto = posts.stream()
				.map(blogPostMapper::convertBlogPostToDto)
				.collect(Collectors.toList());
		
		return postsDto;
	}
	
	
	@GetMapping("/{id}")
	public BlogPostDto getBlogPost(@PathVariable Long id) {
		BlogPost post = blogPostService.getBlogPostById(id);
		
		BlogPostDto postDto = blogPostMapper.convertBlogPostToDto(post);
		Collections.sort(postDto.getContent());
		
		return postDto;
	}
	
	@GetMapping("/tag")
	public List<TagDto> getBlogTags() {
		List<Tag> tags = blogPostService.getTags();
		List<TagDto> tagDtos = tags.stream()
				.map(blogPostMapper::convertTagToDto)
				.collect(Collectors.toList());
		
		return tagDtos;
	}
	
	@GetMapping("/tag/latest")
	public List<TagDto> getLatestBlogTags() {
		List<Tag> tags = blogPostService.getLatestTags();
		List<TagDto> tagDtos = tags.stream()
				.map(blogPostMapper::convertTagToDto)
				.collect(Collectors.toList());
		
		return tagDtos;
	}
	
	@GetMapping("/{id}/tag")
	public TagDto getBlogTag(@PathVariable Long id) {
		Tag tag = blogPostService.getTagById(id);
		
		return blogPostMapper.convertTagToDto(tag);
	}
	
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public BlogPostDto createBlogPost(@RequestBody BlogPostDto postDto) {
		System.out.println(postDto);
		
		BlogPost post = blogPostMapper.convertBlogPostDtoToEntity(postDto);
		BlogPost newPost = blogPostService.createBlogPost(post);
		
		return blogPostMapper.convertBlogPostToDto(newPost);
	}
	
	@DeleteMapping("/parent/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteParent(@PathVariable Long id) {
		blogPostService.deleteParent(id);
	}
	
	@GetMapping("/parent/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ParentDto getParent(@PathVariable Long id) {
		ParentEntity parent = blogPostService.getEntityById(id);
		
		List<ChildDto> childs = new ArrayList<>();
		
		for (ChildEntity child : parent.getChilds()) {
			ChildDto childDto = new ChildDto();
			childDto.setId(child.getId());
			childs.add(childDto);
		}
		
		ParentDto parentDto = new ParentDto();
		parentDto.setId(parent.getId());
		parentDto.setCreatedAt(parent.getCreatedAt());
		parentDto.setChilds(childs);
		
		return parentDto;
	}
	
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public BlogPostDto updateBlogPost(@PathVariable Long id, @RequestBody BlogPostDto postDto) {
		BlogPost post = blogPostMapper.convertBlogPostDtoToEntity(postDto);
		BlogPost updatedPost = blogPostService.updateBlogPost(id, postDto, post);
		
		BlogPostDto updatedPostDto = blogPostMapper.convertBlogPostToDto(updatedPost);
		Collections.sort(updatedPostDto.getContent());
		
		return updatedPostDto;
	}
	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteBlogPost(@PathVariable Long id) {
		blogPostService.deleteBlogPostById(id);
	}
}
