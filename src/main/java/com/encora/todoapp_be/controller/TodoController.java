package com.encora.todoapp_be.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encora.todoapp_be.dto.CreateTodoDTO;
import com.encora.todoapp_be.dto.TodoFilterDTO;
import com.encora.todoapp_be.dto.TodoResponseDTO;
import com.encora.todoapp_be.dto.UpdateTodoDTO;
import com.encora.todoapp_be.model.TodoModel;
import com.encora.todoapp_be.service.TodoService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getFilteredTodos(@ModelAttribute TodoFilterDTO filter) {
        return ResponseEntity.ok(todoService.getTodosWithPagination(filter));
    }

    @PostMapping
    public ResponseEntity<TodoResponseDTO> addTodo(@Valid @RequestBody CreateTodoDTO createTodoDTO) {
        TodoModel newTodo = new TodoModel(
            createTodoDTO.getText(),
            createTodoDTO.getDueDate(),
            createTodoDTO.getPriority()
        );
        TodoModel saved = todoService.addTodo(newTodo);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TodoResponseDTO(saved));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> updateTodo(@PathVariable Long id, @Valid @RequestBody UpdateTodoDTO updateTodo) {
        updateTodo.setId(id);
        Optional<TodoModel> updated = todoService.updateTodo(updateTodo);
        return updated
            .map(todo -> ResponseEntity.ok(convertToDto(todo)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/completed")
    public ResponseEntity<TodoResponseDTO> toggleCompleted(@PathVariable Long id) {
        TodoModel updated = todoService.setCompletedStatus(id);
        
        if (updated != null) {
            return ResponseEntity.ok(convertToDto(updated));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Mapper example (or use MapStruct)
    private TodoResponseDTO convertToDto(TodoModel todo) {
        // Map fields from todo to DTO
        return new TodoResponseDTO(todo);
    }

    // Scripts
    @PostMapping("/scriptCreateTodos")
    public List<TodoModel> createTodos(@RequestBody List<TodoModel> todos) {
        return todoService.addScriptTodos(todos);
    }
}
