package com.encora.todoapp_be.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encora.todoapp_be.model.TodoModel;
import com.encora.todoapp_be.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/getTodos")
    public List<TodoModel> getTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping("/createTodo")
    public TodoModel createTodo(@RequestBody TodoModel todo) {
        return todoService.addTodo(todo);
    }
}
