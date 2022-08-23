package com.saitama.microservices.blogservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TextFragmentDTO {
	
	private String text;
	private boolean bold;
	private boolean italic;
	private boolean underline;
	
}
