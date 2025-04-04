package com.encora.todoapp_be.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.encora.todoapp_be.model.TodoModel;

@Service
public class TodoService {
    private final List<TodoModel> todos = new ArrayList<>();

    public List<TodoModel> getAllTodos() {
        return todos;
    }

    public TodoModel addTodo(TodoModel todo) {
        todo.setId((long) (todos.size() + 1));
        todos.add(todo);
        return todo;
    }
}
