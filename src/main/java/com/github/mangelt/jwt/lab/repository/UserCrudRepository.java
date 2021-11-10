package com.github.mangelt.jwt.lab.repository;

import java.util.Optional;

public interface UserCrudRepository<T>{

		Optional<T> findByEntity(T entity);

}
