package com.encora.todoapp_be.service;

import com.encora.todoapp_be.dto.TodoFilterDTO;
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

    public Map<String, Object> getTodosWithPagination(TodoFilterDTO filter) {
        Integer page = filter.getPage();
        Integer size = filter.getSize();
        String dueDateSort = filter.getDueDateSort();
        String prioritySort = filter.getPrioritySort();
        String text = filter.getText();
        Boolean completed = filter.getCompleted();
        List<String> priorities = filter.getPriorities();

        List<TodoModel> todos = todoRepository.findAll();

        // Build comparator for sorting
        Comparator<TodoModel> comparator = Comparator.comparing(
                TodoModel::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()));

        if ("desc".equalsIgnoreCase(dueDateSort)) {
            comparator = comparator.reversed();
        }

        if (prioritySort != null && !prioritySort.isEmpty()) {
            Comparator<TodoModel> priorityComparator = Comparator.comparingInt(
                    t -> DeclarationUtils.getPriorityValue(t.getPriority()));
            if ("desc".equalsIgnoreCase(prioritySort)) {
                priorityComparator = priorityComparator.reversed();
            }
            comparator = comparator.thenComparing(priorityComparator);
        }

        List<TodoModel> filteredTodos = todos.stream()
                .filter(todo -> text == null || text.isEmpty()
                        || todo.getText().toLowerCase().contains(text.toLowerCase()))
                .filter(todo -> completed == null || todo.isCompleted() == completed)
                .filter(todo -> priorities == null || priorities.isEmpty()
                        || priorities.contains(todo.getPriority().toString()))
                .peek(todo -> todo.setDueDateProximity(DeclarationUtils.getDueDateProximity(todo.getDueDate())))
                .sorted(comparator)
                .toList();

        int fromIndex = Math.min(page * size, filteredTodos.size());
        int toIndex = Math.min(fromIndex + size, filteredTodos.size());

        Map<String, String> metrics = PaginationUtils.getMetricsValue(todos);

        Map<String, Object> response = new HashMap<>();
        response.put("data", filteredTodos.subList(fromIndex, toIndex));
        response.put("totalPages", (int) Math.ceil((double) filteredTodos.size() / size));
        response.putAll(metrics);

        return response;
    }

    public TodoModel addTodo(TodoModel todo) {
        return todoRepository.save(todo);
    }

    public Optional<TodoModel> updateTodo(UpdateTodoDTO dto) {
        return todoRepository.findById(dto.getId())
                .map(existing -> {
                    existing.setText(dto.getText());
                    existing.setPriority(dto.getPriority());
                    existing.setDueDate(dto.getDueDate());
                    return todoRepository.save(existing);
                });
    }

    public TodoModel setCompletedStatus(Long id) {
        Optional<TodoModel> optional = todoRepository.findById(id);
        if (optional.isPresent()) {
            TodoModel todo = optional.get();
            boolean newCompletedStatus = !todo.isCompleted();
            todo.setCompleted(newCompletedStatus);
            if (newCompletedStatus) {
                todo.setDoneDate(); // sets doneDate to now or as implemented
            } else {
                todo.clearDoneDate(); // clear doneDate when undone
            }
            return todoRepository.save(todo);
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