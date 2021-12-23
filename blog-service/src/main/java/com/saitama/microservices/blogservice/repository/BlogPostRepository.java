package com.saitama.microservices.blogservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.saitama.microservices.blogservice.entity.BlogPost;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

	/*
	@Query("SELECT b FROM BlogPost b WHERE b.tag_id = ?1")
	BlogPost findUserByStatus(Integer tagId);
	*/
	
	@Modifying
	@Query("DELETE FROM BlogPost p WHERE p.id = ?1")
	void deleteBlogPostById(Long id);
}
