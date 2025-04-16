package com.encora.todoapp_be.controller;

import com.encora.todoapp_be.dto.UpdateTodoDTO;
import com.encora.todoapp_be.model.TodoModel;
import com.encora.todoapp_be.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    private TodoModel todo;

    @BeforeEach
    void setUp() {
        todo = new TodoModel(
            "Test Todo", 
            null, 
            com.encora.utils.Priority.High
        );
        todo.setId(1L);
    }

    @Test
    void testGetFilteredTodos() throws Exception {
        Map<String, Object> response = new HashMap<>();
        response.put("data", List.of());
        Mockito.when(todoService.getTodosWithPagination(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(response);

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testAddTodo() throws Exception {
        // Test case for valid input
        TodoModel validTodo = new TodoModel("Test Todo", null, com.encora.utils.Priority.High);

        Mockito.when(todoService.addTodo(any(TodoModel.class)))
                .thenReturn(validTodo);

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validTodo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Test Todo"));

        // Test case for empty text
        TodoModel invalidTodo = new TodoModel("", null, com.encora.utils.Priority.High);

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidTodo)))
                .andExpect(status().isBadRequest());

        // Test case for null priority
        TodoModel invalidTodo2 = new TodoModel("Test Todo", null, null);

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidTodo2)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateTodo() throws Exception {
        UpdateTodoDTO updateDto = new UpdateTodoDTO();
        updateDto.setText("Updated");

        TodoModel updatedTodo = new TodoModel("Updated", null, com.encora.utils.Priority.High);

        Mockito.when(todoService.updateTodo(any(UpdateTodoDTO.class)))
                .thenReturn(updatedTodo);

        mockMvc.perform(put("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Updated"));
    }

    @Test
    void testMarkTodoAsDone() throws Exception {
        todo.setCompleted(false);

        Mockito.when(todoService.markTodoAsDone(1L)).thenReturn(todo);

        mockMvc.perform(post("/api/todos/1/done"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void testMarkTodoAsUndone() throws Exception {
        todo.setCompleted(true);

        Mockito.when(todoService.markTodoAsUndone(1L)).thenReturn(todo);

        mockMvc.perform(put("/api/todos/1/undone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(false))
                .andExpect(jsonPath("$.doneDate").doesNotExist());
    }

    @Test
    void testDeleteTodo() throws Exception {
        Mockito.doNothing().when(todoService).deleteTodo(1L);

        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNoContent());
    }
}
