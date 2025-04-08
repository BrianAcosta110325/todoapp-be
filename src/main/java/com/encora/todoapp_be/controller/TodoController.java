package com.encora.todoapp_be.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.encora.todoapp_be.dto.TodoFilterDTO;
import com.encora.todoapp_be.model.TodoModel;
import com.encora.todoapp_be.service.TodoService;

@RestController
@RequestMapping("/api")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos")
    public List<TodoModel> getFilteredTodos(@RequestBody TodoFilterDTO filters) {
        return todoService.getTodosWithPagination(
            filters.getPage(),
            filters.getSize(),
            filters.getDueDateSort(),
            filters.isPrioritySort(),
            filters.getText(),
            filters.getCompleted(),
            filters.getPriority()
        );
    }

    @PostMapping("/todos")
    public TodoModel createTodo(@RequestBody TodoModel todo) {
        return todoService.addTodo(todo);
    }

    // Scripts
    @PostMapping("/scriptCreateTodos")
    public List<TodoModel> createTodos(@RequestBody List<TodoModel> todos) {
        return todoService.addScriptTodos(todos);
    }
}
