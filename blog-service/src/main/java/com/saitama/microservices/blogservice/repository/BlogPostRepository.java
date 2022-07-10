package com.saitama.microservices.blogservice.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.saitama.microservices.blogservice.model.BlogPost;

@Repository
public interface BlogPostRepository extends MongoRepository<BlogPost, Long> {
	
}
