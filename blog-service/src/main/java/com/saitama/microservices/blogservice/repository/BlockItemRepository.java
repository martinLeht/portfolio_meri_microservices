package com.saitama.microservices.blogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.saitama.microservices.blogservice.entity.BlockItem;
import com.saitama.microservices.blogservice.entity.ContentBlock;

@Repository
public interface BlockItemRepository extends JpaRepository<BlockItem, Long> {

//	@Query("DELETE FROM ContentBlock cb WHERE cb.post_id=:postId")
//	public void deleteByBlogPostId(@Param("postId") Long postId);
	
	@Modifying
	@Query("DELETE FROM BlockItem bi WHERE bi.id = ?1")
	void deleteBlockItemById(Long id);
}
