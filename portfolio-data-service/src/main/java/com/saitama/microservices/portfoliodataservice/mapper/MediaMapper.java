package com.saitama.microservices.portfoliodataservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saitama.microservices.commonlib.dto.MediaFileDTO;
import com.saitama.microservices.commonlib.mapper.AbstractObjectMapper;
import com.saitama.microservices.portfoliodataservice.entity.Media;

@Component
public class MediaMapper extends AbstractObjectMapper<Media, MediaFileDTO> {

	@Autowired
	public MediaMapper(ObjectMapper mapper, ModelMapper modelMapper) {
		super(mapper,modelMapper, Media::new, MediaFileDTO::new);
	}

}
