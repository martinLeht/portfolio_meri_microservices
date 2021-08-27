package com.saitama.microservices.blogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saitama.microservices.blogservice.entity.ContentBlock;

@Repository
public interface ContentBlockRepository extends JpaRepository<ContentBlock, Long> {

//	@Query("DELETE FROM ContentBlock cb WHERE cb.post_id=:postId")
//	public void deleteByBlogPostId(@Param("postId") Long postId);
}
