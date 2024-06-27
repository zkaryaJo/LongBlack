package com.longblack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.annotation.Secured;

import com.longblack.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	public Optional<Member> findByEmail(String email);
}
