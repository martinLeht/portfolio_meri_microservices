package com.saitama.microservices.blogservice.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockItemDTO {
	
	private Long id;
	private String type;
	private Long orderNumber;
	private String fileName;
	private String urlLink;
	private List<TextFragmentDTO> textFragments;
	
}
