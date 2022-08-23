package com.saitama.microservices.portfoliodataservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saitama.microservices.portfoliodataservice.entity.Media;


@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

}
