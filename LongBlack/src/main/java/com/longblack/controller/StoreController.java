package com.longblack.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longblack.config.CustomUserDetails;
import com.longblack.domain.Member;
import com.longblack.domain.Store;
import com.longblack.service.MemberService;
import com.longblack.service.StoreService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final MemberService memberService;

    @GetMapping
    public String getAllStores(Model model) {
        model.addAttribute("stores", storeService.getAllStores());
        model.addAttribute("store", new Store()); // 모달에서 사용할 빈 Store 객체
        return "user/store";
    }

    @GetMapping("/edit/{id}")
    @Transactional
    @ResponseBody
    public Store getStoreById(@PathVariable("id") Long id) {
        return storeService.getStoreById(id).orElseThrow(() -> new RuntimeException("Store not found"));
    }

    @PostMapping("/save")
    @Transactional
    public String saveStore(@ModelAttribute Store store, @AuthenticationPrincipal CustomUserDetails userDetails) {
    	Member member = memberService.findByEmail(userDetails.getName());
        storeService.createStore(store, member);
        return "redirect:/store";
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String deleteStore(@PathVariable("id") Long id) {
        storeService.deleteStore(id);
        return "redirect:/store";
    }
}
