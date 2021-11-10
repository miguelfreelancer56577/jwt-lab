package com.github.mangelt.jwt.lab.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.mangelt.jwt.lab.auth.AwsUserPrincipal;
import com.github.mangelt.jwt.lab.entity.DynamoUserEntity;
import com.github.mangelt.jwt.lab.exception.AppException;
import com.github.mangelt.jwt.lab.repository.UserCrudRepository;
import com.github.mangelt.jwt.lab.util.ApiConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Profile(ApiConstants.PROFILE_AWS)
public class AwsUserDetailsImpl implements UserDetailsService{

	@Autowired
	UserCrudRepository<DynamoUserEntity> repository;
	
	@Override
	public UserDetails loadUserByUsername(String username){
		final DynamoUserEntity user = DynamoUserEntity.builder().userId(username).build();
		Optional<DynamoUserEntity> optUser = repository.findByEntity(user);
		if(!optUser.isPresent()) {
			log.debug(ApiConstants.EXP_ERROR_NOT_FOUND_USER.concat(username));
			throw new AppException(ApiConstants.EXP_ERROR_NOT_FOUND_USER.concat(username), new UsernameNotFoundException(ApiConstants.EXP_ERROR_NOT_FOUND_USER.concat(username)));
	 	}
		return new AwsUserPrincipal(optUser.get());
	}

}
