package com.longblack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.longblack.domain.Platform;

@RepositoryRestResource	
public interface PlatformRepository extends JpaRepository<Platform, Long> {

}
