package com.saitama.microservices.authenticationservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saitama.microservices.authenticationservice.dto.TempUserDTO;
import com.saitama.microservices.authenticationservice.entity.TempUser;
import com.saitama.microservices.commonlib.mapper.AbstractObjectMapper;

@Component
public class TempUserMapper extends AbstractObjectMapper<TempUser, TempUserDTO> {

	@Autowired
	public TempUserMapper(ObjectMapper objectMapper, ModelMapper modelMapper) {
		super(objectMapper, modelMapper, TempUser::new, TempUserDTO::new);
	}
}
