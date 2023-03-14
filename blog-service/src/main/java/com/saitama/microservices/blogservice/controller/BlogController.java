package com.saitama.microservices.blogservice.controller;


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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.saitama.microservices.blogservice.dto.BlogPostDTO;
import com.saitama.microservices.blogservice.dto.TagDTO;
import com.saitama.microservices.blogservice.model.ContentBlock;
import com.saitama.microservices.blogservice.model.TextFragment;
import com.saitama.microservices.blogservice.resource.BlockType;
import com.saitama.microservices.blogservice.service.IBlogPostService;
import com.saitama.microservices.commonlib.dto.PageRequestDTO;
import com.saitama.microservices.commonlib.dto.PaginationDTO;
import com.saitama.microservices.commonlib.dto.SearchRequestDTO;

import io.micrometer.core.instrument.util.StringUtils;

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
	public PaginationDTO<TagDTO> getBlogTags(@RequestParam(required = false) Integer page, 
											 @RequestParam(required = false) Integer size) {
		PageRequestDTO pageDto = PageRequestDTO.builder()
				.page(page != null ? page : PageRequestDTO.DEFAULT_PAGE)
				.size(size != null ? size : PageRequestDTO.DEFAULT_SIZE)
				.build();
		return blogPostService.getTags(pageDto);
	}
	
	@GetMapping("/tag/search")
	public PaginationDTO<TagDTO> searchBlogTags(@RequestParam(required = false) String searchTerm, 
												@RequestParam(required = false) Integer page, 
												@RequestParam(required = false) Integer size) {
		if (StringUtils.isBlank(searchTerm)) {
			PageRequestDTO pageDto = PageRequestDTO.builder()
					.page(page != null ? page : PageRequestDTO.DEFAULT_PAGE)
					.size(size != null ? size : PageRequestDTO.DEFAULT_SIZE)
					.build();
			return blogPostService.getTags(pageDto);
		} else {
			SearchRequestDTO searchDto = SearchRequestDTO.searchRequestBuilder()
					.searchTerm(searchTerm)
					.page(page != null ? page : PageRequestDTO.DEFAULT_PAGE)
					.size(size != null ? size : PageRequestDTO.DEFAULT_SIZE)
					.build();
			return blogPostService.searchTags(searchDto);
		}
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
	
	@GetMapping("/flat")
	public List<TagDTO> initFlatContent() {
		return blogPostService.initFlatContent();
	}
}
