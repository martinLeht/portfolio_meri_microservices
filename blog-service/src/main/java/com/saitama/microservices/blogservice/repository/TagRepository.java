package com.saitama.microservices.blogservice.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.saitama.microservices.blogservice.model.Tag;

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {
	
	List<Tag> findByPostId(Long postId);
	Page<Tag> findByContentFlatContainingIgnoreCaseOrPostTitleContainingIgnoreCase(String firstText, String secondText, Pageable pageable);
}
