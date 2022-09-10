package com.saitama.microservices.blogservice.service;

import java.util.List;

import com.saitama.microservices.blogservice.dto.BlogPostDTO;
import com.saitama.microservices.blogservice.dto.TagDTO;
import com.saitama.microservices.commonlib.dto.PageRequestDTO;
import com.saitama.microservices.commonlib.dto.PaginationDTO;
import com.saitama.microservices.commonlib.service.IBaseReadWriteService;


public interface IBlogPostService extends IBaseReadWriteService<BlogPostDTO, Long> {
	
	public List<BlogPostDTO> getBlogPostsByUserId(String userId);
	
	public PaginationDTO<TagDTO> getTags(PageRequestDTO pageDto);
	
	public List<TagDTO> getTagsByUserId(String userId);
	
	public List<TagDTO> getLatestTagsByUserId(String userId);
	
	public TagDTO getTagById(Long id);
}
