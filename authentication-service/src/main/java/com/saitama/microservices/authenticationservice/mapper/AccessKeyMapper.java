package com.saitama.microservices.authenticationservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saitama.microservices.authenticationservice.dto.AccessKeyDTO;
import com.saitama.microservices.authenticationservice.entity.AccessKey;
import com.saitama.microservices.commonlib.mapper.AbstractObjectMapper;

@Component
public class AccessKeyMapper extends AbstractObjectMapper<AccessKey, AccessKeyDTO> {

	
	@Autowired
	public AccessKeyMapper(ObjectMapper objectMapper, ModelMapper modelMapper) {
		super(objectMapper, modelMapper, AccessKey::new, AccessKeyDTO::new);
	}
	
}
