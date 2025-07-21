package com.encora.todoapp_be.dto;

import java.time.Instant;
import java.time.LocalDate;

import com.encora.todoapp_be.model.TodoModel;
import com.encora.utils.Priority;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TodoResponseDTO {

    private Long id;
    private Instant createdAt;
    private String text;
    private LocalDate dueDate;
    private Instant doneDate;
    private Boolean completed;
    private Priority priority;
    private Integer dueDateProximity;

    public TodoResponseDTO(TodoModel model) {
        this.id = model.getId();
        this.createdAt = model.getCreatedAt();
        this.text = model.getText();
        this.dueDate = model.getDueDate();
        this.doneDate = model.getDoneDate();
        this.completed = model.isCompleted();
        this.priority = model.getPriority();
        this.dueDateProximity = model.getDueDateProximity();
    }
}
