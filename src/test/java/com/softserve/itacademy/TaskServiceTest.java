package com.softserve.itacademy;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;

import org.junit.BeforeClass;
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
public class TaskServiceTest {
    private static UserService userService;
    private static ToDoService toDoService;
    private static TaskService taskService;
    private static AnnotationConfigApplicationContext annotationConfigContext;

    @BeforeEach
    public void setupBeforeClass() throws Exception {
        annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        toDoService = annotationConfigContext.getBean(ToDoService.class);
        taskService = annotationConfigContext.getBean(TaskService.class);

    }

    @Test
    public void checkAddTask() {
        User user = new User("Jane", "Watson", "watson23@gmail.com", "12345", new ArrayList<>());
        ToDo toDo = new ToDo("ToDoForToday", LocalDateTime.now(), user, new ArrayList<>());
        Task task = new Task("Cook dinner", Priority.HIGH);
        taskService.addTask(task, toDo);
        assertTrue(toDo.getTasks().contains(task));
    }

    @Test
    public void updateTaskTest(){
        User user = new User("Jane", "Watson", "watson23@gmail.com", "12345", new ArrayList<>());
        Task task = new Task("Cook dinner", Priority.LOW);
        ToDo toDo = new ToDo("ToDoForToday", LocalDateTime.now(), user, new ArrayList<>(List.of(task)));
        userService.addUser(user);
        toDoService.addTodo(toDo, user);
        taskService.addTask(task, toDo);
        taskService.updateTask(task);
        assertSame(Priority.HIGH, task.getPriority());
    }
    @Test
    public void deleteTaskTest(){
        User user = new User("Jane", "Watson", "watson23@gmail.com", "12345", new ArrayList<>());
        Task task = new Task("Cook dinner", Priority.HIGH);
        ToDo toDo = new ToDo("ToDoForToday", LocalDateTime.now(), user, new ArrayList<>(List.of(task)));
        userService.addUser(user);
        toDoService.addTodo(toDo, user);
        System.out.println(toDo);
        taskService.deleteTask(task);
        System.out.println(toDo);
        assertFalse(toDo.getTasks().contains(task));
    }

    @Test
    public void getByToDoTest(){
        User user = new User("Jane", "Watson", "watson23@gmail.com", "12345", new ArrayList<>());
        Task task = new Task("Cook dinner", Priority.HIGH);
        ToDo toDo = new ToDo("ToDoForToday", LocalDateTime.now(), user, new ArrayList<>(List.of(task)));
        userService.addUser(user);
        toDoService.addTodo(toDo, user);
        taskService.addTask(task, toDo);
        List<Task> expected = toDo.getTasks();
        List<Task> actual = taskService.getByToDo(toDo);
        assertEquals(expected, actual);
    }
    @Test
    public void getByToDoNameTest(){
        User user = new User("Jane", "Watson", "watson23@gmail.com", "12345", new ArrayList<>());
        Task task = new Task("Cook dinner", Priority.HIGH);
        ToDo toDo = new ToDo("ToDoForToday", LocalDateTime.now(), user, new ArrayList<>(List.of(task)));
        userService.addUser(user);
        toDoService.addTodo(toDo, user);
        taskService.addTask(task, toDo);
        Task actual = taskService.getByToDoName(toDo, "Cook dinner");
        assertSame(task, actual);
    }
    @Test
    public void getByUserNameTest(){
        User user = new User("Jane", "Watson", "watson23@gmail.com", "12345", new ArrayList<>());
        Task task = new Task("Do homework", Priority.HIGH);
        ToDo toDo = new ToDo("ToDoForToday", LocalDateTime.now(), user, new ArrayList<>(List.of(task)));
        userService.addUser(user);
        toDoService.addTodo(toDo, user);
        taskService.addTask(task, toDo);
        Task actual = taskService.getByUserName(user, "Do homework");
        assertEquals(task, actual);
    }
    @AfterEach
    public void close(){
        annotationConfigContext.close();
    }

}
