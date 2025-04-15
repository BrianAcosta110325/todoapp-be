package com.encora.utils;

import com.encora.todoapp_be.model.TodoModel;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    List<TodoModel> findAll();
    List<TodoModel> saveAll(List<TodoModel> todos);
    TodoModel save(TodoModel todo);
    Optional<TodoModel> findById(Long id);
    void deleteById(Long id);
}