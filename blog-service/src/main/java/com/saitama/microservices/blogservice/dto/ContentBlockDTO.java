package com.saitama.microservices.blogservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentBlockDTO {
	
	private String type;
	private AttachmentDTO attachment;
	private List<TextFragmentDTO> textContent;
	private List<ContentBlockDTO> childNodes;
	
}
