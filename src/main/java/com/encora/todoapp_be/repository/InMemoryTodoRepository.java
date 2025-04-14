package com.encora.todoapp_be.repository;

import com.encora.todoapp_be.model.TodoModel;
import com.encora.utils.TodoRepository;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryTodoRepository implements TodoRepository {
    private final List<TodoModel> todos = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<TodoModel> findAll() {
        return new ArrayList<>(todos);
    }

    @Override
    public TodoModel save(TodoModel todo) {
        if (todo.getId() == null) {
            todo.setId(idGenerator.getAndIncrement());
        }
        todos.removeIf(t -> t.getId().equals(todo.getId()));
        todos.add(todo);
        return todo;
    }

    @Override
    public Optional<TodoModel> findById(Long id) {
        return todos.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    @Override
    public void deleteById(Long id) {
        todos.removeIf(t -> t.getId().equals(id));
    }

    // Scripts
    @Override
    public List<TodoModel> saveAll(List<TodoModel> newTodos) {
        for (TodoModel todo : newTodos) {
            save(todo);
        }
        return newTodos;
    }
}