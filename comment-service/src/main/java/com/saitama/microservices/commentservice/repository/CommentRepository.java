package com.saitama.microservices.commentservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.saitama.microservices.commentservice.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	Page<Comment> findByPostIdAndVerified(UUID postId, boolean isVerified, Pageable pageable);
	
	@Query(value="SELECT * FROM comment c WHERE c.parent_id=?1 AND c.verified=?2", nativeQuery=true)
    Page<Comment> findCommentsByParentIdAndVerifiedWithPagination(String parentId, boolean isVerified, Pageable pageable);
	
	@Query("SELECT e FROM Experience e WHERE e.hidden=?1")
    Page<Comment> findByVerifiedWithPagination(boolean isHidden, Pageable pageable);
	
	List<Comment> findByUuid(UUID uuid);
	
	
	
}
