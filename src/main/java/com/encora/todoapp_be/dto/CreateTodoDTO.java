package com.encora.todoapp_be.dto;

import java.time.LocalDate;

import com.encora.utils.Priority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTodoDTO {

    @NotNull(message = "Text cannot be null")
    @NotBlank(message = "Text cannot be blank")
    @Size(max = 120, message = "Text cannot exceed 120 characters")
    private String text;

    private LocalDate dueDate;

    @NotNull(message = "Priority cannot be null")
    private Priority priority;
}
