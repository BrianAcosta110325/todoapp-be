package com.encora.todoapp_be.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.encora.todoapp_be.dto.UpdateTodoDTO;
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

  public Map<String, Object> getTodosWithPagination(
    Integer page, 
    Integer size, 
    Boolean dueDateSort, 
    Boolean prioritySort, 
    String text, 
    Boolean completed, 
    List<String> priority) {

    // Filter todos based on the provided criteria
    List<TodoModel> filteredTodos = new ArrayList<>();
    for (TodoModel todo : todos) {
      if (text != null && !text.isEmpty() && !todo.getText().toLowerCase().contains(text.toLowerCase())) {
          continue;
      }
      if (completed != null && todo.isCompleted() != completed) {
          continue;
      }
      if (priority != null && !priority.isEmpty() && !priority.contains(todo.getPriority().toString())) {
          continue;
      }
      filteredTodos.add(todo);
    }
    
    if(dueDateSort) {
      // Sort by due date
      filteredTodos.sort((t1, t2) -> t1.getDueDate().compareTo(t2.getDueDate()));
    } 
    if(prioritySort) {
      // Sort by priority
      filteredTodos.sort((t1, t2) -> {
          int priority1 = getPriorityValue(t1.getPriority());
          int priority2 = getPriorityValue(t2.getPriority());
          return Integer.compare(priority1, priority2);
      });
    }
    // Pagination logic
    int fromIndex = Math.min(page * size, filteredTodos.size());
    int toIndex = Math.min(fromIndex + size, filteredTodos.size());

    Map<String, Object> response = new HashMap<>();
    response.put("data", filteredTodos.subList(fromIndex, toIndex));
    response.put("totalPages", (int) Math.ceil((double) (filteredTodos.size()) / size));

    return response;
  }

  public TodoModel addTodo(TodoModel todo) {
    todo.setId((long) (todos.size() + 1));
    todos.add(todo);
    return todo;
  }

  public TodoModel updateTodo(UpdateTodoDTO updateTodo) {
    for (int i = 0; i < todos.size(); i++) {
      if (todos.get(i).getId().equals(updateTodo.getId())) {
        if(updateTodo.getText() != null) {
          todos.get(i).setText(updateTodo.getText());
        }
        if(updateTodo.getDueDate() != null) {
          todos.get(i).setDueDate(updateTodo.getDueDate());
        }
        if(updateTodo.getPriority() != null) {
          todos.get(i).setPriority(updateTodo.getPriority());
        }
        return todos.get(i);
      }
    }
    return null; // or throw an exception if not found
  }

  public TodoModel markTodoAsDone(Long id) {
    for (TodoModel todo : todos) {
      if (todo.getId().equals(id) && todo.isCompleted() == false) {
        todo.setCompleted(true);
        todo.setDoneDate();
        return todo;
      }
    }
    return null;
  }

  public TodoModel markTodoAsUndone(Long id) {
    for (TodoModel todo : todos) {
      if (todo.getId().equals(id) && todo.isCompleted() == true) {
        todo.setCompleted(false);
        todo.setDoneDate();
        return todo;
      }
    }
    return null;
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
