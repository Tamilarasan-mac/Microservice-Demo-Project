package com.demo.orderservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.demo.orderservice.client.UserClient;
import com.demo.orderservice.dto.OrderResponse;
import com.demo.orderservice.model.Order;
import com.demo.orderservice.model.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private UserClient userClient;

    @InjectMocks
    private OrderController controller;

    @Test
    void getAllOrdersReturnsThreeSampleOrders() {
        List<Order> orders = controller.getAllOrders();

        assertEquals(3, orders.size());
        assertEquals("iPhone 15", orders.get(0).getProduct());
    }

    @Test
    void getOrderWithUserCombinesOrderAndUserFromUserService() {
        User user = new User(1L, "Ravi Kumar", "ravi@gmail.com");
        when(userClient.getUserById(1L)).thenReturn(user);

        OrderResponse response = controller.getOrderWithUser(1L);

        assertEquals(1L, response.getOrder().getId());
        assertEquals("DELIVERED", response.getOrder().getStatus());
        assertEquals(user, response.getUser());
        verify(userClient).getUserById(1L);
    }

    @Test
    void getOrderWithUserThrowsWhenOrderDoesNotExistAndSkipsUserLookup() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> controller.getOrderWithUser(99L));

        assertEquals("Order not found: 99", ex.getMessage());
        verify(userClient, never()).getUserById(99L);
    }

    @Test
    void healthReportsServiceIsUp() {
        assertEquals("Order Service is UP!", controller.health());
    }
}
