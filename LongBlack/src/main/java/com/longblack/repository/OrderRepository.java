package com.longblack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.longblack.domain.Order;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long>{

}
