package com.longblack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.longblack.domain.Product;
import com.longblack.domain.Store;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByStore(Store store);
}
