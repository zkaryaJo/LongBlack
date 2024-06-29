package com.longblack.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.longblack.config.CustomUserDetails;
import com.longblack.config.Role;
import com.longblack.domain.Member;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    	String token = "";
        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");

        if(authorization != null && authorization.startsWith("Bearer ")) {
        	token = authorization.split(" ")[1];
        }else {
        	if(request.getCookies() != null) {
        		for(Cookie item : request.getCookies()) {
        			String name = item.getName();
        			if("Authorization".equals(name)) {
        				token = item.getValue().split(" ")[1];
        				break;
        			}
        		}
        	}
        }
        
        if("".equals(token)) {
        	System.out.println("token null");
        	filterChain.doFilter(request, response);
        	
        	return;
        }

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            System.out.println("token expired");
            filterChain.doFilter(request, response);

            return;
        }

        String email = jwtUtil.getEmail(token);
        String name = jwtUtil.getName(token);
        String role = jwtUtil.getRole(token);

        
        
        Member member = Member.builder()
        		.email(email)
        		.name(name)
        		.role(Role.valueOf(role))
        		.build();

        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}

