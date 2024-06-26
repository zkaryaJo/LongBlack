package com.longblack.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longblack.config.CustomUserDetails;
import com.longblack.domain.Member;
import com.longblack.dto.MemberDto;
import com.longblack.jwt.JWTUtil;
import com.longblack.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class UserMainController {
	
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final AesBytesEncryptor aesBytesEncryptor;
	private final JWTUtil jwtUtil;
	
	@GetMapping("/")
    public String redirectToNewsList() {
        return "redirect:/user/login";
    }
	
	/**
	 * 메인 페이지
	 * @param model
	 * @return
	 */
	@GetMapping("/user/main")
	public String main(Model model){
		
		return "user/main";
	}
	
	/* 로그인페이지 */
	@GetMapping("/user/login")
	public String viewLogin(Model model) {
		return "user/login";
	}
	
	/**
	 * 2. 로그인 API
	 * @param loginDto
	 * @return MemberDto.LoginSuccess : 로그인 성공시, accessToken 항목으로 jwt토큰을 전달.
	 */
	@PostMapping("/authenticate")
	@ResponseBody
	public Map login(@Valid @RequestBody MemberDto.Login loginDto, HttpServletResponse response) {
		
		Member member = memberService.findByEmail(loginDto.getEmail());
		Map map = new HashMap<>();
		
		if(passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
			
	        CustomUserDetails customUserDetails = new CustomUserDetails(member);
	        
			//스프링 시큐리티 인증 토큰 생성
	        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(authToken); //세션에 사용자 등록
	        String token = jwtUtil.createJwt(member.getEmail(), member.getRole().toString(), 60*60*1000L);
	        
	        // 쿠키에 인증토큰 저장
 			Cookie cookie = new Cookie("Authorization", "Bearer " + token);
 			cookie.setMaxAge(30 * 60); // 30분 유효
 			cookie.setPath("/");
 			cookie.setSecure(false);
 			response.addCookie(cookie);
 			
 			map.put("result", "success");
 			return map;
		}
		
		map.put("result", "error");
		return map;
	}

}
