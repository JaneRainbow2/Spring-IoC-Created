package com.softserve.itacademy;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        UserService userService = annotationConfigContext.getBean(UserService.class);
        ToDoService toDoService = annotationConfigContext.getBean(ToDoService.class);

        User user1 = new User("John", "Doe", "john.doe@example.com", "password", new ArrayList<>());
        User user2 = new User("Jane", "Smith", "jane.smith@example.com", "password", new ArrayList<>());
        userService.addUser(user1);
        userService.addUser(user2);

        ToDo todo1 = new ToDo("Complete project", LocalDateTime.now(), user1, new ArrayList<>());
        ToDo todo2 = new ToDo("Buy groceries", LocalDateTime.now(), user2, new ArrayList<>());
        toDoService.addTodo(todo1, user1);
        toDoService.addTodo(todo2, user2);

        List<ToDo> allTodos = toDoService.getAll();
        for (ToDo todo : allTodos) {
            System.out.println("ToDo: " + todo.getTitle() + ", Owner: " + todo.getOwner().getFirstName() + " " + todo.getOwner().getLastName());
        }

        List<ToDo> user1Todos = toDoService.getByUser(user1);
        System.out.println("Todos for user " + user1.getFirstName() + " " + user1.getLastName() + ":");
        for (ToDo todo : user1Todos) {
            System.out.println("ToDo: " + todo.getTitle());
        }

        String titleToFind = "Buy groceries";
        ToDo foundTodo = toDoService.getByUserTitle(user2, titleToFind);
        if (foundTodo != null) {
            System.out.println("Found ToDo: " + foundTodo.getTitle() + ", Owner: " + foundTodo.getOwner().getFirstName() + " " + foundTodo.getOwner().getLastName());
        } else {
            System.out.println("ToDo with title '" + titleToFind + "' not found for user " + user2.getFirstName() + " " + user2.getLastName());
        }
        System.out.println("-------------All ToDo:");
        System.out.println(toDoService.getAll());

        System.out.println("------------------Update----------------------");
        System.out.println(todo1);
        System.out.println("------------------After Update----------------------");
        todo1.setTitle("ToDo to update");
        toDoService.updateTodo(todo1, "New Title");
        System.out.println("After update:");
        System.out.println(todo1);



        annotationConfigContext.close();
    }
}
