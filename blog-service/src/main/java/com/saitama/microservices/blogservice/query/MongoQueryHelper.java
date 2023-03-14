package com.saitama.microservices.blogservice.query;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.connection.Stream;

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
	
	public <T> List<T> queryByFieldsContainText(String text, Class<T> entityClass, String ...fieldNames) {
		List<Criteria> criterias = Arrays.stream(fieldNames).map(field -> Criteria.where(field).in(text)).collect(Collectors.toList());
		Criteria criteriaWrapper = new Criteria().orOperator(criterias);
		
		Query query = new Query();
		query.addCriteria(criteriaWrapper);
		List<T> entities = mongoTemplate.find(query, entityClass);
		
		return entities;
	}
	
	public <T> List<T> queryByFieldsContainTextAndSort(String text, Class<T> entityClass, Sort.Direction sortDirection, String sortField, String ...fieldNames) {
		List<Criteria> criterias = Arrays.stream(fieldNames).map(field -> Criteria.where(field).in(text)).collect(Collectors.toList());
		Criteria criteriaWrapper = new Criteria().orOperator(criterias);
		
		Query query = new Query();
		query.addCriteria(criteriaWrapper);
		query.with(Sort.by(sortDirection, sortField));
		List<T> entities = mongoTemplate.find(query, entityClass);
		
		return entities;
	}

}
