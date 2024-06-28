package com.longblack.controller;

import com.longblack.dto.OrderDto;
import com.longblack.domain.Order;
import com.longblack.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String getAllOrders(Model model) {
        List<OrderDto> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("order", new Order()); // 모달에서 사용할 빈 Order 객체
        return "user/order";
    }

    @GetMapping("/edit/{id}")
    @ResponseBody
    public OrderDto getOrderById(@PathVariable("id") Long id) {
        return orderService.getOrderById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @PostMapping("/save")
    public String saveOrder(@ModelAttribute Order order) {
        orderService.createOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}
