package com.longblack.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class MemberDto {

	@Getter @ToString
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SignUp {
		@NotBlank(message = "사용자 아이디는 필수값입니다.") 
		private String email;
		@NotBlank(message = "사용자 이름은 필수값입니다.")
		private String name;
		@NotBlank(message = "사용자 비밀번호는 필수값입니다.")
		private String password;
		
		private String role;
	}
	
	@Getter @ToString
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Login {
		@NotBlank(message = "사용자 아이디는 필수값입니다.")
		private String email;
		@NotBlank(message = "사용자 비밀번호는 필수값입니다.")
		private String password;
	}
	
	//////////////////////////////////////////////////////////
	@Data
	@AllArgsConstructor
	public static class Response{
		private String email;
		private String name;
	}
	
	@Data
	@AllArgsConstructor
	public static class LoginSuccess {
		private String accessToken;
	}
}
