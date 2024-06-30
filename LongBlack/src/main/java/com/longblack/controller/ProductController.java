package com.longblack.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.longblack.domain.Product;
import com.longblack.domain.Member;
import com.longblack.service.ProductService;
import com.longblack.service.MemberService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;
    private final MemberService memberService;
    
    @GetMapping
    public String getAllProducts(Model model, @AuthenticationPrincipal CustomUserDetails user) {
    	
    	Member member = memberService.findByEmail(user.getUsername());
    	model.addAttribute("products", productService.findByStore(member.getStores().get(0)));
    	model.addAttribute("store", member.getStores().get(0));
        model.addAttribute("product", new Product()); // 모달에서 사용할 빈 Product 객체
        return "user/product";
    }
    
    @GetMapping("/edit/{id}")
    @Transactional
    @ResponseBody
    public Map getProductById(@PathVariable("id") Long id, @AuthenticationPrincipal CustomUserDetails user) {
    	
    	Map map = new HashMap<>();
    	Member member = memberService.findByEmail(user.getUsername());
    	map.put("store", member.getStores());
    	map.put("product", productService.getProductById(id).orElseThrow(() -> new RuntimeException("Product not found")));
    	
        return map;
    }

    @PostMapping("/save")
    @Transactional
    public String saveProduct(@ModelAttribute Product product, @AuthenticationPrincipal CustomUserDetails userDetails) {
    	Member member = memberService.findByEmail(userDetails.getName());
        productService.createProduct(product, member);
        return "redirect:/product";
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/product";
    }
}
