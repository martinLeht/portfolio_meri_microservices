package com.saitama.microservices.portfoliodataservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.saitama.microservices.portfoliodataservice.entity.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

	List<Experience> findByUuid(UUID uuid);
	
	@Query("SELECT e FROM Experience e WHERE e.hidden=?1")
    Page<Experience> findByHiddenWithPagination(boolean isHidden, Pageable pageable);
}
