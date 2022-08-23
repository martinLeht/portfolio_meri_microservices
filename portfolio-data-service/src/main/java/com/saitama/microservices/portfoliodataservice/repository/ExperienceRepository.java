package com.saitama.microservices.portfoliodataservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saitama.microservices.portfoliodataservice.entity.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

	List<Experience> findByUuid(UUID uuid);
}
