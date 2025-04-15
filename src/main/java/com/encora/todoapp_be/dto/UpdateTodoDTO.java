package com.encora.todoapp_be.dto;

import java.time.LocalDate;

import com.encora.utils.Priority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateTodoDTO {
    private Long id;
    
    @NotNull(message = "Text cannot be null")
    @NotBlank(message = "Text cannot be blank")
    @Size(max = 120, message = "Text cannot exceed 120 characters")
    private String text;
    
    public UpdateTodoDTO() {
    }

    private LocalDate dueDate;

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
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public Priority getPriority() {
        return priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
