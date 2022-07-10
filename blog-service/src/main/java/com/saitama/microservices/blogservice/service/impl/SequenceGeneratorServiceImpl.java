package com.saitama.microservices.blogservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.saitama.microservices.blogservice.model.Sequence;
import com.saitama.microservices.blogservice.service.ISequenceGeneratorService;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;

@Service
public class SequenceGeneratorServiceImpl implements ISequenceGeneratorService {

	private MongoOperations mongoOperations;
	
	
	@Autowired
	public SequenceGeneratorServiceImpl(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}



	@Override
	public long generateSequence(String seqName) {
		Sequence counter = mongoOperations.findAndModify(
				query(where("_id").is(seqName)),
	      		new Update().inc("seq",1), options().returnNew(true).upsert(true),
	      		Sequence.class);
	    return !Objects.isNull(counter) ? counter.getSeq() : 1;
	}

}
