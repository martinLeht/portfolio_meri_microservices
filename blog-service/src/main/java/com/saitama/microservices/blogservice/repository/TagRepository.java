package com.saitama.microservices.blogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saitama.microservices.blogservice.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
