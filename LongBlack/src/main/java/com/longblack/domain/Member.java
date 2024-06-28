package com.longblack.domain;

import java.util.List;

import org.hibernate.envers.Audited;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.longblack.config.Role;
import com.longblack.dto.MemberDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Audited
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name = "MEMBER")
public class Member  {
	
	@Id @Column(name="member_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique=true)
	private String email;
	
	private String name;
	
	@JsonIgnore
	private String password;
	private String address;
    private String phone;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @OneToMany(mappedBy = "member" , cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Order> orders;

    @OneToMany(mappedBy = "member" , cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Review> reviews;

	
	public static Member toEntity(MemberDto.SignUp signUpDto, PasswordEncoder passwordEncoder, AesBytesEncryptor aesBytesEncryptor) {
		
		return Member.builder()
				.email(signUpDto.getEmail())
				.password(passwordEncoder.encode(signUpDto.getPassword()))
				.name(signUpDto.getName())
				.role(Role.USER)
				.build();
	}
}
