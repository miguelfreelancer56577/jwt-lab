package com.github.mangelt.jwt.lab.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.mangelt.jwt.lab.auth.AzureUserPrincipal;
import com.github.mangelt.jwt.lab.entity.TableStorageUser;
import com.github.mangelt.jwt.lab.exception.AppException;
import com.github.mangelt.jwt.lab.repository.UserCrudRepository;
import com.github.mangelt.jwt.lab.util.ApiConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Profile(ApiConstants.PROFILE_AZURE)
public class AzureUserDetailsImpl implements UserDetailsService{

	@Autowired
	UserCrudRepository<TableStorageUser> repository;
	
	@Override
	public UserDetails loadUserByUsername(String username){
		final TableStorageUser tableStorageUser = new TableStorageUser(username);
		final Optional<TableStorageUser> findByEntity = repository.findByEntity(tableStorageUser);
		if(!findByEntity.isPresent()) {
			log.debug(ApiConstants.EXP_ERROR_NOT_FOUND_USER.concat(username));
			throw new AppException(ApiConstants.EXP_ERROR_NOT_FOUND_USER.concat(username), new UsernameNotFoundException(ApiConstants.EXP_ERROR_NOT_FOUND_USER.concat(username)));
	 	}
		return new AzureUserPrincipal(findByEntity.get());
	}

}
