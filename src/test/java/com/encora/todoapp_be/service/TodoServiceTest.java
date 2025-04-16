package com.encora.todoapp_be.service;

import com.encora.todoapp_be.model.TodoModel;
import com.encora.todoapp_be.dto.UpdateTodoDTO;
import com.encora.utils.TodoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TodoServiceTest {

    private TodoService todoService;
    private TodoRepository todoRepository;
    private TodoModel todoModel;

    @BeforeEach
    void setUp() {
        todoRepository = mock(TodoRepository.class);
        todoService = new TodoService(todoRepository);
        todoModel = new TodoModel(
            "Test Todo", 
            null, 
            com.encora.utils.Priority.High
        );
        todoModel.setId(1L);
        todoModel.setCompleted(false);
    }

    @Test
    void testGetTodosWithPaginationAndFilters() {
        todoModel.setDueDate(LocalDate.now().plusDays(1));

        TodoModel todo2 = todoModel.clone();
        todo2.setId(2L);
        todo2.setText("Test Todo 2");
        todo2.setCompleted(true);
        todo2.setPriority(com.encora.utils.Priority.Medium);

        List<TodoModel> todos = List.of(todoModel, todo2);
        when(todoRepository.findAll()).thenReturn(todos);

        Map<String, Object> result = todoService.getTodosWithPagination(
                0,       
                10,      
                "desc",   
                "asc",   
                "test",     
                true,     
                List.of("Medium")
        );

        List<TodoModel> data = (List<TodoModel>) result.get("data");

        assertEquals(1, data.size());
        assertEquals("Test Todo 2", data.get(0).getText());
        assertEquals(1, result.get("totalPages"));
        assertNotNull(result.get("averageTimeDifference"));
        assertNotNull(result.get("averageLowTimeDifference"));
        assertNotNull(result.get("averageMediumTimeDifference"));
        assertNotNull(result.get("averageHighTimeDifference"));
    }

    @Test
    void testAddTodo() {
        when(todoRepository.save(todoModel)).thenReturn(todoModel);

        TodoModel result = todoService.addTodo(todoModel);
        assertEquals("Test Todo", result.getText());
        verify(todoRepository).save(todoModel);
    }

    @Test
    void testUpdateTodo() {
        UpdateTodoDTO update = new UpdateTodoDTO();
        update.setId(1L);
        update.setText("New text");

        when(todoRepository.findById(1L)).thenReturn(Optional.of(todoModel));
        when(todoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        TodoModel result = todoService.updateTodo(update);

        assertNotNull(result);
        assertEquals("New text", result.getText());
    }

    @Test
    void testMarkTodoAsDone() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todoModel));
        when(todoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        TodoModel result = todoService.markTodoAsDone(1L);

        assertTrue(result.isCompleted());
        assertNotNull(result.getDoneDate());
        verify(todoRepository).save(todoModel);
    }

    @Test
    void testMarkTodoAsUndone() {
        todoModel.setCompleted(true);
        todoModel.setDoneDate();

        when(todoRepository.findById(1L)).thenReturn(Optional.of(todoModel));
        when(todoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        TodoModel result = todoService.markTodoAsUndone(1L);

        assertFalse(result.isCompleted());
        assertNull(result.getDoneDate());
        verify(todoRepository).save(todoModel);
    }

    @Test
    void testDeleteTodo() {
        todoService.deleteTodo(1L);
        verify(todoRepository).deleteById(1L);
    }

    @Test
    void testAddScriptTodos() {
        List<TodoModel> todos = Arrays.asList(new TodoModel("a",null, com.encora.utils.Priority.High), new TodoModel("a",null, com.encora.utils.Priority.High));

        when(todoRepository.saveAll(todos)).thenReturn(todos);

        List<TodoModel> result = todoService.addScriptTodos(todos);
        assertEquals(2, result.size());
        verify(todoRepository).saveAll(todos);
    }
}
