package com.encora.todoapp_be.repository;

import com.encora.todoapp_be.model.TodoModel;
import com.encora.utils.Priority;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTodoRepositoryTest {

    private InMemoryTodoRepository todoRepository;

    @BeforeEach
    void setUp() {
        todoRepository = new InMemoryTodoRepository();
    }

    @Test
    void testFindAll() {
        todoRepository.save(createTodo());
        todoRepository.save(createTodo());

        List<TodoModel> allTodos = todoRepository.findAll();

        assertEquals(2, allTodos.size());
    }

    @Test
    void testSave() {
        TodoModel todo = createTodo();
        TodoModel savedTodo = todoRepository.save(todo);

        assertNotNull(savedTodo.getId());
        assertEquals(todo.getText(), savedTodo.getText());
    }

    @Test
    void testDeleteById() {
        TodoModel todo = todoRepository.save(createTodo());
        Long id = todo.getId();

        todoRepository.deleteById(id);

        assertTrue(todoRepository.findById(id).isEmpty());
    }

    @Test
    void testUpdateExistingTodo() {
        TodoModel todo = todoRepository.save(createTodo());
        Long id = todo.getId();

        todo.setText("Updated");
        todoRepository.save(todo);  

        Optional<TodoModel> found = todoRepository.findById(id);
        assertTrue(found.isPresent());
        assertEquals("Updated", found.get().getText());
    }

    @Test
    void testSaveAll() {
        List<TodoModel> batch = List.of(
                createTodo(),
                createTodo(),
                createTodo()
        );

        List<TodoModel> saved = todoRepository.saveAll(batch);

        assertEquals(3, saved.size());
        assertEquals(3, todoRepository.findAll().size());
    }

    // Helper method
    private TodoModel createTodo() {
        TodoModel todo = new TodoModel("text", null, Priority.Medium);
        return todo;
    }
}
