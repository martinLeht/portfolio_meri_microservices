package com.saitama.microservices.portfoliodataservice.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saitama.microservices.commonlib.mapper.AbstractObjectMapper;
import com.saitama.microservices.portfoliodataservice.dto.CommentDTO;
import com.saitama.microservices.portfoliodataservice.entity.Comment;

@Component
public class CommentMapper extends AbstractObjectMapper<Comment, CommentDTO> {

	@Autowired
	public CommentMapper(ObjectMapper mapper, ModelMapper modelMapper) {
		super(mapper, modelMapper, Comment::new, CommentDTO::new);
	}

}
