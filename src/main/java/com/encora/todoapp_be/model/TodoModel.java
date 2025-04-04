package com.encora.todoapp_be.model;

import java.time.Instant;
import java.time.LocalDateTime;
import javax.validation.constraints.Size;
import com.encora.utils.Priority;

public class TodoModel {
    private Long id;
    private Instant createdAt;
    @Size(max = 120, message = "Text cannot exceed 120 characters")
    private String text;
    private LocalDateTime dueDate;
    private LocalDateTime doneDate;
    private boolean completed;
    private Priority priority;

    public TodoModel(String text, LocalDateTime dueDate, LocalDateTime doneDate, boolean completed, Priority priority) {
        this.createdAt = Instant.now();
        this.text = text;
        this.dueDate = dueDate;
        this.doneDate = doneDate;
        this.completed = completed;
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

    public LocalDateTime getDueDate() {
        return this.dueDate;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getDoneDate() {
        return this.doneDate;
    }
    public void setDoneDate(LocalDateTime doneDate) {
        this.doneDate = doneDate;
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
}