package com.softserve.itacademy;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class ToDoServiceTest {

    private AnnotationConfigApplicationContext annotationConfigContext;
    private UserService userService;
    private ToDoService toDoService;

    @BeforeEach
    public void setup() {
        annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        toDoService = annotationConfigContext.getBean(ToDoService.class);
    }

    @Test
    public void checkAddToDo() {
        User user = new User("John", "Doe", "john@example.com", "password", new ArrayList<>());
        userService.addUser(user);

        ToDo todo = new ToDo("Test ToDo", LocalDateTime.now(), user, new ArrayList<>());
        toDoService.addTodo(todo, user);

        List<ToDo> todos = toDoService.getAll();
        assertTrue(todos.contains(todo));
    }

    @Test
    public void checkUpdateToDo() {
        User user = new User("Jane", "Doe", "jane@example.com", "password", new ArrayList<>());
        userService.addUser(user);

        ToDo todo = new ToDo("Test ToDo", LocalDateTime.now(), user, new ArrayList<>());
        toDoService.addTodo(todo, user);

        String updatedTitle = "Updated ToDo";
        toDoService.updateTodo(todo, updatedTitle);

        ToDo updatedTodo = toDoService.getAll().get(0);
        assertEquals(updatedTitle, updatedTodo.getTitle());
    }

    @Test
    public void checkDeleteToDo() {
        User user = new User("Alice", "Smith", "alice@example.com", "password", new ArrayList<>());
        userService.addUser(user);

        ToDo todo = new ToDo("Test ToDo", LocalDateTime.now(), user, new ArrayList<>());
        toDoService.addTodo(todo, user);

        toDoService.deleteTodo(todo);

        List<ToDo> todos = toDoService.getAll();
        assertFalse(todos.contains(todo));
    }

    @AfterEach
    public void tearDown() {
        annotationConfigContext.close();
    }
}
