package com.saitama.microservices.blogservice.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "portfolio-data-service")
public interface PortfolioDataServiceClient {
	
	@DeleteMapping("/comment/post/{postId}")
	public void deleteCommentsByPostId(@PathVariable("postId") UUID postId);
}
