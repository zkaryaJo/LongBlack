package com.longblack.domain;

import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.longblack.config.Role;
import com.longblack.dto.MemberDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원가입 사용자
 */
@Getter @Setter @ToString
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name = "MEMBER")
public class Member  {
	
	@Id @Column(name="member_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique=true)
	private String email;
	
	private String name;
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;
	
	public static Member toEntity(MemberDto.SignUp signUpDto, PasswordEncoder passwordEncoder, AesBytesEncryptor aesBytesEncryptor) {
		
		return Member.builder()
				.email(signUpDto.getEmail())
				.password(passwordEncoder.encode(signUpDto.getPassword()))
				.name(signUpDto.getName())
				.role(Role.USER)
				.build();
	}
}
