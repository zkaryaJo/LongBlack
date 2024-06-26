package com.longblack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.longblack.domain.OrderItem;

@RepositoryRestResource
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
