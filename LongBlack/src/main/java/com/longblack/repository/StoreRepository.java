package com.longblack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.longblack.domain.Store;

@RepositoryRestResource
public interface StoreRepository extends JpaRepository<Store, Long>{

}
