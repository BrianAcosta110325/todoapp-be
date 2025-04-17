# Breakable Toy I - Todo App back-end

This is the backend service for the Todo application.  
It provides a RESTful API to manage tasks (Todos), including creation, updating, deletion, filtering, and metrics.

The backend is built using **Spring Boot** and follows a modular architecture for easy maintenance and scalability.

## Features

- **Full CRUD Operations**: Create, read, update, and delete tasks.
- **Advanced Filtering**: Filter tasks by priority, text, and completion status.
- **State Management**: Mark tasks as completed or not completed.
- **Pagination and Sorting**: Support for paginated and sorted results.
- **Metrics**: Calculate average completion time by priority.
- **Due Date Classification**: Logic to classify tasks based on due date proximity.

## Prerequisites

- **Java 17 or higher**: Ensure Java is installed on your system.
- **Maven**: Use the included Maven wrapper (`mvnw`) to run Maven without a global installation.

## Getting Started

### Run the Application

To start the application, use the following command:

```bash
./mvnw spring-boot:run
```

The application will be available at `http://localhost:9090`.

## API Endpoints

### Todos

- **GET /api/todos**: Retrieve a list of tasks with support for filters, pagination, and sorting.
- **POST /api/todos**: Create a new task.
- **PUT /api/todos/{id}**: Update an existing task.
- **POST /api/todos/{id}/done**: Mark a task as completed.
- **PUT /api/todos/{id}/undone**: Mark a task as not completed.
- **DELETE /api/todos/{id}**: Delete a task.

### Scripts

- **POST /api/scriptCreateTodos**: Create multiple tasks in a single request.

## Testing

Run unit tests with the following command:

```bash
./mvnw test
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   ├── com.encora.todoapp_be/  # Main application code
│   │   ├── com.encora.utils/      # Shared utilities
│   ├── resources/                 # Configuration files
├── test/                          # Unit tests
```