package com.saitama.microservices.blogservice.repository;


import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.saitama.microservices.blogservice.model.BlogPost;

@Repository
public interface BlogPostRepository extends MongoRepository<BlogPost, UUID> {
	
}
