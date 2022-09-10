package com.saitama.microservices.commentservice.service.impl;

import java.util.UUID;

import com.saitama.microservices.commentservice.dto.CommentDTO;
import com.saitama.microservices.commentservice.service.ICommentService;
import com.saitama.microservices.commonlib.dto.PageRequestDTO;
import com.saitama.microservices.commonlib.dto.PaginationDTO;

public class CommentServiceImpl implements ICommentService {

	@Override
	public PaginationDTO<CommentDTO> getPaginated(PageRequestDTO pageDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommentDTO getById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommentDTO create(CommentDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommentDTO update(UUID id, CommentDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(UUID id) {
		// TODO Auto-generated method stub
		
	}

}
