package com.longblack.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private String memberName;
    private Date orderDate;
    private double totalAmount;
    private String status;
    private List<OrderItemDto> orderItems;
}
