package com.softserve.itacademy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.softserve.itacademy.model.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;

@Service
public class TaskServiceImpl implements TaskService {

    private ToDoService toDoService;
    private List<Task> tasks;

    @Autowired
    public TaskServiceImpl(ToDoService toDoService) {
        this.toDoService = toDoService;
        tasks = new ArrayList<>();
    }

    public Task addTask(Task task, ToDo todo) {
        if(todo != null){
            todo.getTasks().add(task);
            tasks.add(task);
            return task;
        }else {
            throw new IllegalArgumentException("Illegal todo");
        }

    }

    public Task updateTask(Task task) {
        // TODO
        if(tasks.contains(task)){
            task.setPriority(Priority.HIGH);
        }
        return task;
    }

    public void deleteTask(Task task) {
        for(ToDo temp : toDoService.getAll()){
            if(temp.getTasks().remove(task)){
                tasks.remove(task);
                return;
            }
        }
        throw new IllegalArgumentException("Illegal task");
    }

    public List<Task> getAll() {
        return tasks;
    }

    public List<Task> getByToDo(ToDo todo) {
        int index = toDoService.getAll().indexOf(todo);
        if(index != -1){
            return toDoService.getAll().get(index).getTasks();
        }
        throw new IllegalArgumentException("Illegal todo");
    }

    public Task getByToDoName(ToDo todo, String name) {
        for(Task t : getByToDo(todo)){
            if(t.getName().equals(name)){
                return t;
            }
        }
        throw new IllegalArgumentException("Illegal name");
    }

    public Task getByUserName(User user, String name) {
        return toDoService.getByUser(user).stream().flatMap(toDo -> toDo.getTasks().stream())
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> list.get(0)
                ));
    }

}
