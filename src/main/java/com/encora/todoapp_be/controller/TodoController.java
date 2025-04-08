package com.encora.todoapp_be.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/getWithPagination")
    public List<TodoModel> getTodosWithPagination(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        // Link structure: /getWithPagination?page=1&size=5
        return todoService.getTodosWithPagination(page, size);
    }

    @PostMapping("/createTodo")
    public TodoModel createTodo(@RequestBody TodoModel todo) {
        return todoService.addTodo(todo);
    }

    // Scripts
    @PostMapping("/scriptCreateTodos")
    public List<TodoModel> createTodos(@RequestBody List<TodoModel> todos) {
        return todoService.addScriptTodos(todos);
    }
}
