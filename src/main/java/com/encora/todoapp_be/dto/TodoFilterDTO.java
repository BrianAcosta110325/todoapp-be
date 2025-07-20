package com.encora.todoapp_be.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TodoFilterDTO {

    private Integer page = 0;
    private Integer size = 10;
    private String dueDateSort;    // Expected values: "asc", "desc", or null
    private String prioritySort;   // Expected values: "asc", "desc", or null
    private String text;
    private Boolean completed;
    private List<String> priorities = new ArrayList<>();

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if (page != null && page >= 0) {
            this.page = page;
        }
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        if (size != null && size > 0) {
            this.size = size;
        }
    }

    public String getDueDateSort() {
        return dueDateSort;
    }

    public void setDueDateSort(String dueDateSort) {
        this.dueDateSort = dueDateSort;
    }

    public String getPrioritySort() {
        return prioritySort;
    }

    public void setPrioritySort(String prioritySort) {
        this.prioritySort = prioritySort;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public List<String> getPriorities() {
        return priorities;
    }

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
