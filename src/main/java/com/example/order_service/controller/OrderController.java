package com.demo.orderservice.controller;

import com.demo.orderservice.client.UserClient;
import com.demo.orderservice.dto.OrderResponse;
import com.demo.orderservice.model.Order;
import com.demo.orderservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private UserClient userClient;

    private List<Order> orders = Arrays.asList(
            new Order(1L, 1L, "iPhone 17", 89999.0, "DELIVERED"),
            new Order(2L, 2L, "MacBook M5 Pro ", 250000.0, "SHIPPED"),
            new Order(3L, 1L, "AirPods", 15000.0, "PENDING")
    );

    // Just orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orders;
    }

    // Order + User details (calls user-service internally)
    @GetMapping("/{id}/details")
    public OrderResponse getOrderWithUser(@PathVariable Long id) {
        Order order = orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));

        // Feign Client use panni user-service call panrom
        User user = userClient.getUserById(order.getUserId());

        return new OrderResponse(order, user);
    }

    @GetMapping("/health")
    public String health() {
        return "Order Service is UP!";
    }
}