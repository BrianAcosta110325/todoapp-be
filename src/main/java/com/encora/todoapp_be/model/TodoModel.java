package com.encora.todoapp_be.model;

import java.time.Instant;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.encora.utils.Priority;

public class TodoModel {
    private Long id;
    private Instant createdAt;

    @NotNull(message = "Text cannot be null")
    @NotBlank(message = "Text cannot be blank")
    @Size(max = 120, message = "Text cannot exceed 120 characters")
    private String text;

    private LocalDate dueDate;

    private Instant doneDate;

    private Boolean completed;

    @NotNull(message = "Priority cannot be null")
    private Priority priority;

    private Integer dueDateProximity;

    public TodoModel(String text, LocalDate dueDate, Priority priority) {
        this.createdAt = Instant.now();
        this.text = text;
        this.dueDate = dueDate;
        this.completed = false;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "TodoModel{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", text='" + text + '\'' +
                ", dueDate=" + dueDate +
                ", doneDate=" + doneDate +
                ", completed=" + completed +
                ", priority=" + priority +
                '}';
    }

    // Getters and Setters
    public Long getId() { 
        return this.id; 
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Instant getDoneDate() {
        return this.doneDate;
    }
    public void setDoneDate() {
        this.doneDate = Instant.now();
    }

    public boolean isCompleted() {
        return this.completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Priority getPriority() {
        return this.priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    public Integer getDueDateProximity() {
        return this.dueDateProximity;
    }
    public void setDueDateProximity(Integer dueDateProximity) {
        this.dueDateProximity = dueDateProximity;
    }
}