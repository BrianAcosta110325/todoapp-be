package com.encora.todoapp_be.dto;

import java.time.LocalDateTime;

import com.encora.utils.Priority;

import jakarta.validation.constraints.Size;

public class UpdateTodoDTO {
    private Long id;

    @Size(max = 120, message = "Text cannot exceed 120 characters")
    private String text;

    private LocalDateTime dueDate;

    private Priority priority;

    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    public Priority getPriority() {
        return priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
