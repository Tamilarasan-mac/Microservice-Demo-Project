package com.demo.userservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.demo.userservice.model.User;
import java.util.List;
import org.junit.jupiter.api.Test;

class UserControllerTest {

    private final UserController controller = new UserController();

    @Test
    void getAllUsersReturnsThreeSampleUsers() {
        List<User> users = controller.getAllUsers();

        assertEquals(3, users.size());
        assertEquals("Ravi Kumar", users.get(0).getName());
    }

    @Test
    void getUserByIdReturnsMatchingUser() {
        User user = controller.getUserById(2L);

        assertEquals(2L, user.getId());
        assertEquals("Priya Devi", user.getName());
        assertEquals("priya@gmail.com", user.getEmail());
    }

    @Test
    void getUserByIdThrowsWhenUserDoesNotExist() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> controller.getUserById(99L));

        assertEquals("User not found: 99", ex.getMessage());
    }

    @Test
    void healthReportsServiceIsUp() {
        assertEquals("User Service is UP!", controller.health());
    }
}
