package com.saitama.microservices.blogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saitama.microservices.blogservice.entity.TextFragment;

@Repository
public interface TextFragmentRepository extends JpaRepository<TextFragment, Long>{

//	@Query("DELETE FROM TextFragment txtFrag WHERE txtFrag.block_id=:blockId")
//	public void deleteByContentBlockId(@Param("blockId") Long blockId);
}
