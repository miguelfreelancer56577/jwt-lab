package com.github.mangelt.jwt.lab.repository.impl;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.github.mangelt.jwt.lab.entity.TableStorageUser;
import com.github.mangelt.jwt.lab.exception.AppException;
import com.github.mangelt.jwt.lab.repository.UserCrudRepository;
import com.github.mangelt.jwt.lab.util.ApiConstants;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.TableOperation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Repository
@Profile(ApiConstants.PROFILE_AZURE)
public class TableStorageUserRepository implements UserCrudRepository<TableStorageUser> {

	private final CloudTable cloudTable;
	
	@Override
	public Optional<TableStorageUser> findByEntity(TableStorageUser entity) {
		final TableOperation operation = TableOperation.retrieve(entity.getPartitionKey(), entity.getRowKey(), TableStorageUser.class);
		final Optional<TableStorageUser> rs; 
		try {
			rs = Optional.ofNullable(cloudTable.execute(operation).getResultAsType());
		} catch (StorageException e) {
			log.error(ApiConstants.EXP_ERROR_FIND_USER, e);
			throw new AppException(ApiConstants.EXP_ERROR_FIND_USER, e);
		}
		return rs;
	}

}
