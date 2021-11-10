package com.github.mangelt.jwt.lab.repository.impl;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.github.mangelt.jwt.lab.entity.DynamoUserEntity;
import com.github.mangelt.jwt.lab.repository.UserCrudRepository;
import com.github.mangelt.jwt.lab.util.ApiConstants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
@Profile(ApiConstants.PROFILE_AWS)
public class DynamoDbUserRepository implements UserCrudRepository<DynamoUserEntity> {

	private final DynamoDBMapper dynamoDBMapper;
	
	@Override
	public Optional<DynamoUserEntity> findByEntity(DynamoUserEntity entity) {
		return Optional.ofNullable(dynamoDBMapper.load(entity)); 
	}

}
