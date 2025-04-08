package com.encora.todoapp_be.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.encora.todoapp_be.model.TodoModel;
import com.encora.utils.Priority;

@Service
public class TodoService {
    private final List<TodoModel> todos = new ArrayList<>();

    public List<TodoModel> getAllTodos() {
        return todos;
    }

    private int getPriorityValue(Priority priority) {
        switch (priority) {
            case High:
                return 1;
            case Medium:
                return 2;
            case Low:
                return 3;
            default:
                throw new IllegalArgumentException("Invalid priority: " + priority);
        }
    }

    public List<TodoModel> getTodosWithPagination(int page, int size, Boolean dueDateSort, Boolean prioritySort, String text, Boolean completed, String priority) {
        if(dueDateSort) {
            // Sort by due date
            todos.sort((t1, t2) -> t1.getDueDate().compareTo(t2.getDueDate()));
        } 
        if(prioritySort) {
            // Sort by priority
            todos.sort((t1, t2) -> {
                int priority1 = getPriorityValue(t1.getPriority());
                int priority2 = getPriorityValue(t2.getPriority());
                return Integer.compare(priority1, priority2);
            });
        }
        // Pagination logic
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
