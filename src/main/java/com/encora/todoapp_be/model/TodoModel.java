package com.encora.todoapp_be.model;

import java.sql.Date;
import javax.validation.constraints.Size;

import com.encora.utils.Priority;

public class TodoModel {
    private Long id;
    private Date createdAt;
    @Size(max = 120, message = "Text cannot exceed 120 characters")
    private String text;
    private Date dueDate;
    private Date doneDate;
    private boolean completed;
    private Priority priority;

    public TodoModel() {}

    // Getters and Setters
    public Long getId() { 
        return this.id; 
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public Date getDueDate() {
        return this.dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDoneDate() {
        return this.doneDate;
    }
    public void setDoneDate(Date doneDate) {
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