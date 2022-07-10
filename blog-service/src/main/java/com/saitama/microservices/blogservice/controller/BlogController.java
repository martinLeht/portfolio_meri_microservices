package com.saitama.microservices.blogservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.saitama.microservices.blogservice.dto.PageRequestDto;
import com.saitama.microservices.blogservice.dto.PaginationDto;
import com.saitama.microservices.blogservice.dto.TagDto;
import com.saitama.microservices.blogservice.service.IBlogPostService;

@RestController
@RequestMapping("/blog")
public class BlogController {
	
	private final DiscoveryClient discoveryClient;
	private final IBlogPostService blogPostService;
	
	@Autowired
	public BlogController(DiscoveryClient discoveryClient,
							IBlogPostService blogPostService) {
		this.discoveryClient = discoveryClient;
		this.blogPostService = blogPostService;
	}
	
	@GetMapping("/services")
	public ResponseEntity<List<List<ServiceInstance>>> getServices() {
		List<String> serviceIds = this.discoveryClient.getServices();
		System.out.println("FETCHING SERVICES");
		
		List<List<ServiceInstance>> services = new ArrayList<>();
		
		for (String serviceId : serviceIds) {
			System.out.println(serviceId);
			services.add(this.discoveryClient.getInstances(serviceId));
		}
		return ResponseEntity.ok(services);
	}


	@GetMapping("/")
	public List<BlogPostDto> getBlogPosts() {
		List<BlogPostDto> postsDto = blogPostService.getBlogPosts();
		return postsDto;
	}
	
	
	@GetMapping("/{id}")
	public BlogPostDto getBlogPost(@PathVariable Long id) {
		BlogPostDto postDto = blogPostService.getBlogPostById(id);
		return postDto;
	}
	
	@GetMapping("/tag")
	public PaginationDto<TagDto> getBlogTags(@RequestBody(required = false) PageRequestDto pageDto) {
		PaginationDto<TagDto> tagsPaginationDto = blogPostService.getTags(pageDto);
		return tagsPaginationDto;
	}
	
	/*
	@GetMapping("/tag/latest")
	public List<TagDto> getLatestBlogTags() {
		List<TagDto> tagDtos = blogPostService.getLatestTags();		
		return tagDtos;
	}
	*/
	
	@GetMapping("/{id}/tag")
	public TagDto getBlogTag(@PathVariable Long id) {
		TagDto tagDto = blogPostService.getTagById(id);
		return tagDto;
	}
	
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public BlogPostDto createBlogPost(@RequestBody BlogPostDto postDto) {
		BlogPostDto newPostDto = blogPostService.createBlogPost(postDto);
		return newPostDto;
	}
	
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public BlogPostDto updateBlogPost(@PathVariable Long id, @RequestBody BlogPostDto postDto) {
		BlogPostDto updatedPostDto = blogPostService.updateBlogPost(id, postDto);
		return updatedPostDto;
	}
	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteBlogPost(@PathVariable Long id) {
		blogPostService.deleteBlogPostById(id);
	}
	
}
