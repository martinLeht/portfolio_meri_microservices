package com.saitama.microservices.commentservice.service;

import java.util.UUID;

import com.saitama.microservices.commentservice.dto.CommentDTO;
import com.saitama.microservices.commonlib.dto.PageRequestDTO;
import com.saitama.microservices.commonlib.dto.PaginationDTO;
import com.saitama.microservices.commonlib.service.IBaseReadWriteService;

public interface ICommentService extends IBaseReadWriteService<CommentDTO, UUID> {
	
	PaginationDTO<CommentDTO> getPaginatedPostComments(UUID postId, PageRequestDTO pageDto);
	
	PaginationDTO<CommentDTO> getPaginatedThreadComments(UUID parentId, PageRequestDTO pageDto);
}
