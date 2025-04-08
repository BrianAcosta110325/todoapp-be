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

    public List<TodoModel> getTodosWithPagination(int page, int size) {
        int fromIndex = Math.min(page * size, todos.size());
        int toIndex = Math.min(fromIndex + size, todos.size());
        
        return todos.subList(fromIndex, toIndex);
    }

    public TodoModel addTodo(TodoModel todo) {
        todo.setId((long) (todos.size() + 1));
        todos.add(todo);
        return todo;
    }

    // Scripts
    public List<TodoModel> addScriptTodos(List<TodoModel> todos) {
        for (TodoModel todo : todos) {
            todo.setId((long) (this.todos.size() + 1));
            this.todos.add(todo);
        }
        return todos;
    }
}
