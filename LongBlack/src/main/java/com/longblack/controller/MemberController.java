package com.longblack.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longblack.config.CustomUserDetails;
import com.longblack.domain.Member;
import com.longblack.dto.MemberDto;
import com.longblack.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class MemberController {

 	private final MemberService memberService;
	private final BCryptPasswordEncoder passwordEncoder;
	private final AesBytesEncryptor aesBytesEncryptor;

	/**
	 * 1. 회원가입 API
	 * @param signUpDto
	 * @return MemberDto.Response 회원가입 후, response dto
	 */
	@PostMapping("/signup")
	@ResponseBody
	public ResponseEntity<?> signup(@Valid @RequestBody MemberDto.SignUp signUpDto) {

		Member entity = Member.toEntity(signUpDto, passwordEncoder, aesBytesEncryptor);
		Member member = memberService.save(entity);
		MemberDto.Response responseDto = new MemberDto.Response(member.getEmail(), member.getName());
		
		return ResponseEntity.ok(responseDto);
	}
	
	/**
	 * 이메일 인증
	 * @param request
	 * @param param
	 * @return
	 */
	@PostMapping("/checkMail")
	@ResponseBody
	public Map<String, Object> SendMail(HttpServletRequest request, @RequestBody Map param) {
 
		String Authkey = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		 if (!memberService.isEmailExist(""+param.get("email"))) {
			 Authkey = memberService.sendEmail(param);
			 map.put("result", "success");
			 map.put("Authkey", Authkey);
		 }else {
			 map.put("result", "exist");
		 }
		return map;
	}
	
	/**
	 * 회원 정보 수정
	 * @param param
	 * @param model
	 * @return
	 */
	@PostMapping("/update")
	@ResponseBody
	@Transactional
	public Map updateMember(Authentication authentication, @RequestBody Map param, Model model){
		String result="";
	    Map map = new HashMap<>();
	    
	    Member foundMember = memberService.findByEmail(authentication.getName()); 
		
	    if (foundMember != null) {
	    	
		    String rawPassword = (String) param.get("password");
		    
		    foundMember.setPassword(passwordEncoder.encode(rawPassword));
		    foundMember.setName(""+param.getOrDefault("name", foundMember.getName()));
		    
	    	result = "success";
	    }else {
	    	result = "error";
	    }

	    map.put("result", result);
	    
        return map;

	}
	
	/**
	 * 회원 상태 변경
	 * @param param
	 * @param model
	 * @return
	 */
//	@PostMapping("/update/status")
//	@ResponseBody
//	@Transactional
//	public String updateMemberStatus(Authentication authentication, @RequestBody Map param, Model model){
//
//		
//		Member foundMember = memberService.findByEmail(authentication.getName());
//		    
//		    if (foundMember != null) {
//
//		        if (param.containsKey("pw") && param.get("pw") != null) {
//		           // memberService.updateMemberStatus(param);
//		        }
//		        
//		        int updatedStatus = memberService.getStatusById(param);
//		        
//		        if (updatedStatus == 0) {
//		            model.addAttribute("success", "탈퇴 되었습니다.");
//		        }
//		        
//		        return "redirect:/";
//		    }
//
//		    model.addAttribute("error", "다시 확인 해주세요.");
//		    return "redirect:/";
//
//	}
	
	/**
	 * 마이페이지
	 * @param model
	 * @return
	 */
	@GetMapping("/mypage")
	public String mypageForm(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model){
		model.addAttribute("user", customUserDetails);
		return "user/mypage";
	}
	
}