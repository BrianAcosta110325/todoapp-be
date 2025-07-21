package com.encora.todoapp_be.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoFilterDTO {
    private Integer page = 0;
    private Integer size = 10;
    private String text;
    private Boolean completed;
    private List<String> priorities;
    private String dueDateSort;
    private String prioritySort;

    /**
     * Set priorities from a comma-separated string, e.g. "High,Medium,Low"
     */
    public void setPriorities(String priorities) {
        if (priorities != null && !priorities.isBlank()) {
            this.priorities = Arrays.asList(priorities.split(","));
            // Optionally trim spaces:
            this.priorities.replaceAll(String::trim);
        } else {
            this.priorities = new ArrayList<>();
        }
    }
}
