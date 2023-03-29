package com.saitama.microservices.blogservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.saitama.microservices.blogservice.model.Tag;

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, UUID> {
	
	Page<Tag> findByContentFlatContainingIgnoreCaseOrPostTitleContainingIgnoreCase(String firstText, String secondText, Pageable pageable);
	Optional<Tag> findByPostTitle(String postTitle);
}
