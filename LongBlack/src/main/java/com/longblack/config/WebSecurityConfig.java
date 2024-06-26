package com.longblack.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.longblack.jwt.JWTFilter;
import com.longblack.jwt.JWTUtil;
import com.longblack.jwt.LoginFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	@Value("${symmetric.key}")
	private String symmetrickey;
	@Value("${symmetric.salt}")
	private String salt;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtil jwtUtil;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.cors(cors -> cors.disable())
			.csrf(csrf -> csrf.disable())
			.headers(header -> header.frameOptions(opt -> opt.disable()));

		http
			.authorizeHttpRequests((auth) -> auth
					.requestMatchers("/", "/user/login", "/user/authenticate", "/member/checkMail", "/member/signup").permitAll()
					.requestMatchers( "/css/**", "/js/**", "/images/**").permitAll() 
					.requestMatchers("/h2-console/**").permitAll() // h2-console
					.requestMatchers("/longblack/**").permitAll() // swagger
					.requestMatchers("/error").permitAll()	//error 발생 시 security에서 /error 페이지로 보내며 403 forbidden 이 뜸 
				.anyRequest().authenticated()
			)
			.formLogin(form -> form
					.loginPage("/user/login")
			)
			.logout(logout -> logout
					.logoutUrl("/logout")
					.logoutSuccessUrl("/user/login?logout=true")
					.deleteCookies("JSESSIONID")
					.deleteCookies("Authorization")
					.permitAll()) // h2-console 접속용
			.httpBasic(basic -> basic.disable());

		http
			.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class)
			.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class)
			.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션 설정

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AesBytesEncryptor aesBytesEncryptor() {
		return new AesBytesEncryptor(symmetrickey, salt);
	}
}
