package com.saitama.microservices.blogservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.saitama.microservices.blogservice.utils.BlogPostMapper;

@Configuration
public class AppBeanConfig {

	
	@Bean
	public BlogPostMapper blogPostMapper() {
		return new BlogPostMapper();
	}
}
