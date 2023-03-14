package com.saitama.microservices.blogservice.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saitama.microservices.blogservice.dto.TagDTO;
import com.saitama.microservices.blogservice.model.Tag;
import com.saitama.microservices.commonlib.mapper.AbstractObjectMapper;

@Component
public class TagMapper extends AbstractObjectMapper<Tag, TagDTO> {

	@Autowired
	public TagMapper(ObjectMapper mapper, ModelMapper modelMapper) {
		super(mapper, modelMapper, Tag::new, TagDTO::new);
	}

}
