package com.softserve.itacademy;

import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import com.softserve.itacademy.service.impl.TaskServiceImpl;
import com.softserve.itacademy.service.impl.ToDoServiceImpl;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.softserve.itacademy.service")
public class Config {

  @Bean
  public ToDoService toDoServiceImpl(UserService userService) {
    return new ToDoServiceImpl(userService);
  }

  @Bean
  public TaskService taskServiceImpl(ToDoService toDoService){
      return new TaskServiceImpl(toDoService);
  }
}
