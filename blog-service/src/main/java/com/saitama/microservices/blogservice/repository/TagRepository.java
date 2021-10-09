package com.saitama.microservices.blogservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.saitama.microservices.blogservice.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM Tag t ORDER BY t.created_at DESC LIMIT 3")
	List<Tag> findLatestTags();

}
