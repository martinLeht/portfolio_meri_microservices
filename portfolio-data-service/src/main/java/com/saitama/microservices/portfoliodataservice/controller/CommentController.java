package com.saitama.microservices.portfoliodataservice.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.saitama.microservices.commonlib.dto.PageRequestDTO;
import com.saitama.microservices.commonlib.dto.PaginationDTO;
import com.saitama.microservices.portfoliodataservice.dto.CommentDTO;
import com.saitama.microservices.portfoliodataservice.service.ICommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	
	private final ICommentService commentService;
	
	
	@Autowired
	public CommentController(ICommentService commentService) {
		this.commentService = commentService;
	}
	
	@GetMapping("/post/{postId}")
	public PaginationDTO<CommentDTO> getPostComments(@PathVariable String postId, 
													 @RequestParam Integer page, @RequestParam Integer size) {
		PageRequestDTO pageDto = PageRequestDTO.builder()
				.page(page != null ? page : PageRequestDTO.DEFAULT_PAGE)
				.size(size != null ? size : PageRequestDTO.DEFAULT_SIZE)
				.build();		
		return commentService.getPaginatedPostComments(UUID.fromString(postId), pageDto);
	}
	
	
	@GetMapping("/{parentId}/thread")
	public PaginationDTO<CommentDTO> getThreadComments(@PathVariable String parentId,
													   @RequestParam Integer page, @RequestParam Integer size) {
		PageRequestDTO pageDto = PageRequestDTO.builder()
				.page(page != null ? page : PageRequestDTO.DEFAULT_PAGE)
				.size(size != null ? size : PageRequestDTO.DEFAULT_SIZE)
				.build();				
		return commentService.getPaginatedThreadComments(UUID.fromString(parentId), pageDto);
	}
	
	
	@GetMapping("/{id}")
	public CommentDTO getComment(@PathVariable String id) {		
		return commentService.getById(UUID.fromString(id));
	}
	
	
	@PostMapping("/")
	public CommentDTO createComment(@RequestBody CommentDTO commentDto) {		
		return commentService.create(commentDto);
	}
	
	
	@PostMapping("/{parentId}/thread")
	public CommentDTO createThreadComment(@PathVariable String parentId, @RequestBody CommentDTO commentDto) {		
		return commentService.create(commentDto);
	}
	
	
	@PutMapping("/{id}")
	public CommentDTO updateComment(@PathVariable String id, @RequestBody CommentDTO commentDto) {		
		return commentService.update(UUID.fromString(id), commentDto);
	}
	

	@DeleteMapping("/{id}")
	public void deleteComment(@PathVariable String id) {		
		commentService.delete(UUID.fromString(id));
	}
	
}
