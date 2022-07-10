package com.saitama.microservices.blogservice.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class MongoQueryHelper {
	
		
	private final MongoTemplate mongoTemplate;
	
	
	@Autowired
	public MongoQueryHelper(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public <T> List<T> queryByField(String fieldName, String fieldValue, Class<T> entityClass) {
		Query query = new Query();
		query.addCriteria(Criteria.where(fieldName).is(fieldValue));
		List<T> entities = mongoTemplate.find(query, entityClass);
		
		return entities;
	}
	
	public <T> List<T> queryByFieldAndSort(String fieldName, String fieldValue, Sort.Direction sortDirection, String sortField, Class<T> entityClass) {
		Query query = new Query();
		query.addCriteria(Criteria.where(fieldName).is(fieldValue));
		query.with(Sort.by(sortDirection, sortField));
		List<T> entities = mongoTemplate.find(query, entityClass);
		
		return entities;
	}

}
