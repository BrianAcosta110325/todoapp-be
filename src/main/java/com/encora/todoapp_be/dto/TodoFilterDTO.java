package com.encora.todoapp_be.dto;

public class TodoFilterDTO {
    private int page = 0;
    private int size = 10;
    private String dueDateSort = "";
    private String prioritySort = "";
    private String text = "";
    private Boolean completed;
    private String priority;

    // Constructor
    public TodoFilterDTO() {
    }
    public TodoFilterDTO(int page, int size, String dueDateSort, String prioritySort, String text, Boolean completed, String priority) {
        this.page = page;
        this.size = size;
        this.dueDateSort = dueDateSort;
        this.prioritySort = prioritySort;
        this.text = text;
        this.completed = completed;
        this.priority = priority;
    }

    // Getters y Setters
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public String getDueDateSort() {
        return dueDateSort;
    }
    public void setDueDateSort(String dueDateSort) {
        this.dueDateSort = dueDateSort;
    }
    public String isPrioritySort() {
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
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }

    // PrintFilter
    @Override
    public String toString() {
        return "TodoFilterDTO{" +
                "page=" + page +
                ", dueDateSort=" + dueDateSort +
                ", prioritySort=" + prioritySort +
                ", text='" + text + '\'' +
                ", completed=" + completed +
                ", priority='" + priority + '\'' +
                '}';
    }
}

