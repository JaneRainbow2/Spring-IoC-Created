package com.softserve.itacademy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnitPlatform.class)
public class UserServiceTest {
    private static UserService userService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        annotationConfigContext.close();
    }

    @Test
    public void checkAddUser() {
        User user = new User("John", "Doe", "john.doe@example.com", "password", new ArrayList<>());
        User expected = user;
        User actual = userService.addUser(user);
        Assertions.assertEquals(expected, actual, "User should be added successfully");
    }

    @Test
    public void checkUpdateUser() {
        User user = new User("John", "Doe", "john.doe@example.com", "password", new ArrayList<>());
        userService.addUser(user);

        User updatedUser = new User("John", "Doe", "john.doe@example.com", "newpassword", new ArrayList<>());
        User expected = updatedUser;
        User actual = userService.updateUser(updatedUser);

        Assertions.assertEquals(expected, actual, "User should be updated successfully");
    }

    @Test
    public void checkDeleteUser() {
        User user = new User("John", "Doe", "john.doe@example.com", "password", new ArrayList<>());
        userService.addUser(user);

        userService.deleteUser(user);

        List<User> users = userService.getAll();
        Assertions.assertFalse(users.contains(user), "User should be deleted");
    }

    @Test
    public void checkGetAll() {
        User user1 = new User("John", "Doe", "john.doe@example.com", "password", new ArrayList<>());
        User user2 = new User("Jane", "Smith", "jane.smith@example.com", "password", new ArrayList<>());
        userService.addUser(user1);
        userService.addUser(user2);

        List<User> allUsers = userService.getAll();

        Assertions.assertTrue(allUsers.contains(user1));
        Assertions.assertTrue(allUsers.contains(user2));
    }
}
