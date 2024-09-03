package com.softserve.itacademy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;

@Service
public class ToDoServiceImpl implements ToDoService {
    private UserService userService;
    private List<ToDo> todos;

    @Autowired
    public ToDoServiceImpl(UserService userService) {
        this.userService = userService;
        this.todos = new ArrayList<>();
    }

    @Override
    public ToDo addTodo(ToDo todo, User user) {
        if (user != null && userService.getAll().contains(user)) {
            todo.setOwner(user);
            todos.add(todo);
            return todo;
        } else {
            throw new IllegalArgumentException("User does not exist");
        }
    }

    @Override
    public ToDo updateTodo(ToDo todo, String newTitle) {
        for (ToDo existingTodo : todos) {
            if (existingTodo.equals(todo)) {
                existingTodo.setTitle(newTitle);
                return existingTodo;
            }
        }
        throw new IllegalArgumentException("ToDo does not exist");
    }

    @Override
    public void deleteTodo(ToDo todo) {
        todos.remove(todo);
    }

    @Override
    public List<ToDo> getAll() {
        return todos;
    }

    @Override
    public List<ToDo> getByUser(User user) {
        List<ToDo> userTodos = new ArrayList<>();
        for (ToDo todo : todos) {
            if (todo.getOwner().equals(user)) {
                userTodos.add(todo);
            }
        }
        return userTodos;
    }

    @Override
    public ToDo getByUserTitle(User user, String title) {
        for (ToDo todo : todos) {
            if (todo.getOwner().equals(user) && todo.getTitle().equals(title)) {
                return todo;
            }
        }
        return null;
    }
}
