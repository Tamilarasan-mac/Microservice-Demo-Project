package com.demo.orderservice.dto;

import com.demo.orderservice.model.Order;
import com.demo.orderservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Order order;
    private User user;  // user-service-la irundhu varum
}