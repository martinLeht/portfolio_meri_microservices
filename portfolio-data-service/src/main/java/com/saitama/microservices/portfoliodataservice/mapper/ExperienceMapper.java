package com.saitama.microservices.portfoliodataservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saitama.microservices.commonlib.mapper.AbstractObjectMapper;
import com.saitama.microservices.portfoliodataservice.dto.ExperienceDTO;
import com.saitama.microservices.portfoliodataservice.entity.Experience;

@Component
public class ExperienceMapper extends AbstractObjectMapper<Experience, ExperienceDTO> {

	@Autowired
	public ExperienceMapper(ObjectMapper objectMapper, ModelMapper modelMapper) {
		super(objectMapper, modelMapper, Experience::new, ExperienceDTO::new);
	}
	
	@Override
	public Experience updateFromDtoToModel(Experience model, ExperienceDTO dto) throws JsonProcessingException {
		dto.setUuid(null);
        return super.updateFromDtoToModel(model, dto);
    }

}
