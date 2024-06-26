package com.longblack.service;

import java.util.Map;
import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.longblack.domain.Member;
import com.longblack.repository.MemberRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final JavaMailSender javaMailSender;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(email).orElseThrow();

		return User.builder()
				.username(member.getEmail())
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
	}

	public Member save(Member member) {
		validateMember(member);
		return memberRepository.save(member);
	}
	
	public boolean isEmailExist(String email) {
		return memberRepository.findByEmail(email).isPresent(); 
	}
	
	private void validateMember(Member member)  {
		
		if(isEmailExist(member.getEmail())) 
			throw new IllegalStateException("이미 가입된 회원입니다");
	}
	
	public Member findByEmail(String email) {
		
		return memberRepository.findByEmail(email).orElseThrow();
	}
	
	public String sendEmail(Map param) {
		
		Random random = new Random();
		String Authkey = "";

		MimeMessage message = javaMailSender.createMimeMessage();
		
		try {
		    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		    helper.setTo((String) param.get("email"));

		    for (int i = 0; i < 3; i++) {
		        int index = random.nextInt(25) + 65; // A~Z까지 랜덤 알파벳 생성
		        Authkey += (char) index;
		    }
		    
		    int numIndex = random.nextInt(8999) + 1000; // 4자리 정수를 생성
		    Authkey += numIndex;
		    helper.setSubject("[LongBlack] 회원가입을 위한 이메일 인증.");

		    String msgg="";
		    msgg+= "<div style='margin:20px;'>";
		    msgg+= "<h4> 안녕하세요 LongBlack 입니다.</h4>";
		    msgg+= "<br>";
		    msgg+= "<p>이메일 인증을 위해 아래 코드를 입력해주세요.<p>";
		    msgg+= "<br>";
		    msgg+= "<p>감사합니다.<p>";
		    msgg+= "<br>";
		    msgg+= "<div align='center' style='border:1px solid black; font-family:verdana;'>";
		    msgg+= "<h3 style='color:blue;'>이메일 인증 코드입니다.</h3>";
		    msgg+= "<div style='font-size:130%'>";
		    msgg+= "CODE : <strong>";
		    msgg+= Authkey+ "</strong><div><br/> ";
		    msgg+= "</div>";

		    helper.setText(msgg, true);
		    
		} catch (MessagingException e) {
		    e.printStackTrace();
		}

		javaMailSender.send(message);

		return Authkey;

	}
	
}
