package com.encora.todoapp_be.service;

import com.encora.todoapp_be.dto.UpdateTodoDTO;
import com.encora.todoapp_be.model.TodoModel;
import com.encora.utils.DeclarationUtils;
import com.encora.utils.PaginationUtils;
import com.encora.utils.TodoRepository;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Map<String, Object> getTodosWithPagination(
      Integer page, 
      Integer size, 
      String dueDateSort, 
      String prioritySort, 
      String text, 
      Boolean completed, 
      List<String> priority) {

        List<TodoModel> todos = todoRepository.findAll();
        List<TodoModel> filteredTodos = new ArrayList<>();

        for (TodoModel todo : todos) {
            if (text != null && 
                !text.isEmpty() && 
                !todo.getText().toLowerCase().contains(text.toLowerCase())) {
                continue;
            }
            if (completed != null && todo.isCompleted() != completed) {
                continue;
            }
            if (priority != null && 
                !priority.isEmpty() && 
                !priority.contains(todo.getPriority().toString())) {
                continue;
            }
            todo.setDueDateProximity(DeclarationUtils.getDueDateProximity(todo.getDueDate()));
            filteredTodos.add(todo);
        }

        if (dueDateSort != null && !dueDateSort.isEmpty()) {
            filteredTodos.sort(Comparator.comparing(TodoModel::getDueDate, Comparator.nullsLast(Comparator.naturalOrder())));
            if (dueDateSort.equals("asc")) Collections.reverse(filteredTodos);
        }

        if (prioritySort != null && !prioritySort.isEmpty()) {
            filteredTodos.sort(Comparator.comparingInt(t -> DeclarationUtils.getPriorityValue(t.getPriority())));
            if (prioritySort.equals("desc")) Collections.reverse(filteredTodos);
        }

        // Pagination
        int fromIndex = Math.min(page * size, filteredTodos.size());
        int toIndex = Math.min(fromIndex + size, filteredTodos.size());

        // Metrics
        Map<String, String> metrics = PaginationUtils.getMetricsValue(todos);

        // Response as Map
        Map<String, Object> response = new HashMap<>();
        response.put("data", filteredTodos.subList(fromIndex, toIndex));
        response.put("totalPages", (int) Math.ceil((double) (filteredTodos.size()) / size));
        response.putAll(metrics);

        return response;
    }

    public TodoModel addTodo(TodoModel todo) {
        return todoRepository.save(todo);
    }

    public TodoModel updateTodo(UpdateTodoDTO updateTodo) {
        Optional<TodoModel> optional = todoRepository.findById(updateTodo.getId());
        if (optional.isPresent()) {
            TodoModel todo = optional.get();
            if (updateTodo.getText() != null) todo.setText(updateTodo.getText());
            if (updateTodo.getDueDate() != null) todo.setDueDate(updateTodo.getDueDate());
            if (updateTodo.getPriority() != null) todo.setPriority(updateTodo.getPriority());
            return todoRepository.save(todo);
        }
        return null;
    }

    public TodoModel markTodoAsDone(Long id) {
        Optional<TodoModel> optional = todoRepository.findById(id);
        if (optional.isPresent()) {
            TodoModel todo = optional.get();
            if (!todo.isCompleted()) {
                todo.setCompleted(true);
                todo.setDoneDate();
                return todoRepository.save(todo);
            }
        }
        return null;
    }

    public TodoModel markTodoAsUndone(Long id) {
        Optional<TodoModel> optional = todoRepository.findById(id);
        if (optional.isPresent()) {
            TodoModel todo = optional.get();
            if (todo.isCompleted()) {
                todo.setCompleted(false);
                todo.setDoneDate();
                return todoRepository.save(todo);
            }
        }
        return null;
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    // Scripts
    public List<TodoModel> addScriptTodos(List<TodoModel> todos) {
        return todoRepository.saveAll(todos);
    }
}