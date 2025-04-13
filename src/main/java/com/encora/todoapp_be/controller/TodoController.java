package com.encora.todoapp_be.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.encora.todoapp_be.dto.UpdateTodoDTO;
import com.encora.todoapp_be.model.TodoModel;
import com.encora.todoapp_be.service.TodoService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos")
    public Map<String, Object> getFilteredTodos(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "") String dueDateSort,
            @RequestParam(required = false, defaultValue = "") String prioritySort,
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false, name = "priorities") String priority
        ) {
        // Use example: http://localhost:8080/api/todos?page=0&size=10&dueDateSort=true&prioritySort=true&text=example&completed=true&priorities=High,Medium
        return todoService.getTodosWithPagination(
            page,
            size,
            dueDateSort,
            prioritySort,
            text,
            completed,
            priority != null ? List.of(priority.split(",")) : Collections.emptyList()
        );
    }
  
    @PostMapping("/todos")
    public ResponseEntity<TodoModel> addTodo(@Valid @RequestBody TodoModel todo) {
        TodoModel newTodo = todoService.addTodo(todo);
        return ResponseEntity.ok(newTodo);
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<TodoModel> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTodoDTO updateTodo) {

        updateTodo.setId(id);
        TodoModel updatedTodo = todoService.updateTodo(updateTodo);
        return ResponseEntity.ok(updatedTodo);
    }

    @PostMapping("/todos/{id}/done")
    public TodoModel markTodoAsDone(@PathVariable Long id) {
        return todoService.markTodoAsDone(id);
    }

    @PutMapping("/todos/{id}/undone")
    public TodoModel markTodoAsUndone(@PathVariable Long id) {
        return todoService.markTodoAsUndone(id);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    // Scripts
    @PostMapping("/scriptCreateTodos")
    public List<TodoModel> createTodos(@RequestBody List<TodoModel> todos) {
        return todoService.addScriptTodos(todos);
    }
}
