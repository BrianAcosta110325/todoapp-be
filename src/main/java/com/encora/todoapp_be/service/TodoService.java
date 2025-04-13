package com.encora.todoapp_be.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.encora.todoapp_be.dto.UpdateTodoDTO;
import com.encora.todoapp_be.model.TodoModel;
import com.encora.utils.DeclarationUtils;
import com.encora.utils.PaginationUtils;

@Service
public class TodoService {
  private final List<TodoModel> todos = new ArrayList<>();

  public Map<String, Object> getTodosWithPagination(
    Integer page, 
    Integer size, 
    String dueDateSort, 
    String prioritySort, 
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
      todo.setDueDateProximity(DeclarationUtils.getDueDateProximity(todo.getDueDate()));
      filteredTodos.add(todo);
    }
    
    if(dueDateSort != null && !dueDateSort.equals("")) {
      if (dueDateSort.equals("asc")) {
        filteredTodos.sort((t1, t2) -> {
          if (t1.getDueDate() == null && t2.getDueDate() == null) return 0;
          if (t1.getDueDate() == null) return 1;
          if (t2.getDueDate() == null) return -1;
          return t1.getDueDate().compareTo(t2.getDueDate());
        });
      } else if (dueDateSort.equals("desc")) {
        filteredTodos.sort((t1, t2) -> {
          if (t1.getDueDate() == null && t2.getDueDate() == null) return 0;
          if (t1.getDueDate() == null) return 1;
          if (t2.getDueDate() == null) return -1;
          return t2.getDueDate().compareTo(t1.getDueDate());
        });
      }
    } 
    if(prioritySort != null && !prioritySort.equals("")) {
      if(prioritySort.equals("asc")) {
        filteredTodos.sort((t1, t2) -> DeclarationUtils.getPriorityValue(t1.getPriority()) - DeclarationUtils.getPriorityValue(t2.getPriority()));
      } else if(prioritySort.equals("desc")) {
        filteredTodos.sort((t1, t2) -> DeclarationUtils.getPriorityValue(t2.getPriority()) - DeclarationUtils.getPriorityValue(t1.getPriority()));
      }
    }

    // Pagination logic
    int fromIndex = Math.min(page * size, filteredTodos.size());
    int toIndex = Math.min(fromIndex + size, filteredTodos.size());

    Map<String, String> metrics = PaginationUtils.getMetricsValue(todos);

    Map<String, Object> response = new HashMap<>();
    response.put("data", filteredTodos.subList(fromIndex, toIndex));
    response.put("totalPages", (int) Math.ceil((double) (filteredTodos.size()) / size));
    response.putAll(metrics);

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

  public void deleteTodo(Long id) {
    for (int i = 0; i < todos.size(); i++) {
      if (todos.get(i).getId().equals(id)) {
        todos.remove(i);
        return;
      }
    }
    // Optionally, throw an exception if the todo is not found
    throw new RuntimeException("Todo not found with id: " + id);
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
