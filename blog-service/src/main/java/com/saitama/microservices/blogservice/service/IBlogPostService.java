package com.saitama.microservices.blogservice.service;

import java.util.List;
import java.util.UUID;

import com.saitama.microservices.blogservice.dto.BlogPostDTO;
import com.saitama.microservices.blogservice.dto.TagDTO;
import com.saitama.microservices.commonlib.dto.PageRequestDTO;
import com.saitama.microservices.commonlib.dto.PaginationDTO;
import com.saitama.microservices.commonlib.dto.SearchRequestDTO;
import com.saitama.microservices.commonlib.service.IBaseReadWriteService;


public interface IBlogPostService extends IBaseReadWriteService<BlogPostDTO, UUID> {
	
	public List<BlogPostDTO> getBlogPostsByUserId(String userId);
	
	public PaginationDTO<TagDTO> getTags(PageRequestDTO pageDto);
	
	public PaginationDTO<TagDTO> searchTags(SearchRequestDTO searchDto);
}
