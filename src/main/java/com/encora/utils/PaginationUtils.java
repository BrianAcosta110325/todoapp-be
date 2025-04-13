package com.encora.utils;

import java.util.List;
import java.util.Map;

import com.encora.todoapp_be.model.TodoModel;

public class PaginationUtils {
  public static Map<String, String> getMetricsValue(List<TodoModel> todos) {
    // Filter completed todos
    List<TodoModel> completedTodos = todos.stream()
        .filter(TodoModel::isCompleted)
        .toList();

    // Filter todos by priority
    List<TodoModel> lowTodos = completedTodos.stream()
        .filter(todo -> todo.getPriority() == Priority.Low)
        .toList();
    List<TodoModel> mediumTodos = completedTodos.stream()
        .filter(todo -> todo.getPriority() == Priority.Medium)
        .toList();
    List<TodoModel> highTodos = completedTodos.stream() 
        .filter(todo -> todo.getPriority() == Priority.High)
        .toList();

    // Calculate the time difference between DoneDate and DueDate for lowTodos
    List<Long> lowTodosTimeDifferences = lowTodos.stream()
      .map(todo -> {
        if (todo.getDoneDate() != null && todo.getCreatedAt() != null) {
          return (Long) (todo.getDoneDate().toEpochMilli() - todo.getCreatedAt().toEpochMilli());
        }
        return 0L; // Default to 0 if dates are null
      })
      .toList();
    List<Long> mediumTodosTimeDifferences = mediumTodos.stream()
    .map(todo -> {
      if (todo.getDoneDate() != null && todo.getCreatedAt() != null) {
        return (Long) (todo.getDoneDate().toEpochMilli() - todo.getCreatedAt().toEpochMilli());
      }
      return 0L; // Default to 0 if dates are null
    })
    .toList();
    List<Long> highTodosTimeDifferences = highTodos.stream()
    .map(todo -> {
      if (todo.getDoneDate() != null && todo.getCreatedAt() != null) {
        return (Long) (todo.getDoneDate().toEpochMilli() - todo.getCreatedAt().toEpochMilli());
      }
      return 0L; // Default to 0 if dates are null
    })
    .toList();
    
    // Calculate the average time difference for lowTodos
    double averageLowTodosTimeDifference = lowTodosTimeDifferences.stream()
      .mapToLong(Long::longValue)
      .average()
      .orElse(0.0);
    
    double averageMediumTodosTimeDifference = mediumTodosTimeDifferences.stream()
      .mapToLong(Long::longValue)
      .average()
      .orElse(0.0);
    double averageHighTodosTimeDifference = highTodosTimeDifferences.stream()
      .mapToLong(Long::longValue)
      .average()
      .orElse(0.0);

    double averageTimeDifference = (averageLowTodosTimeDifference + averageMediumTodosTimeDifference + averageHighTodosTimeDifference) / 3;

    String humanReadableLowTime = getHumanReadableTime(averageLowTodosTimeDifference);
    String humanReadableMediumTime = getHumanReadableTime(averageMediumTodosTimeDifference);
    String humanReadableHighTime = getHumanReadableTime(averageHighTodosTimeDifference);
    String humanReadableTime = getHumanReadableTime(averageTimeDifference);

    return Map.of(
        "averageTimeDifference", humanReadableTime,
        "averageLowTimeDifference", humanReadableLowTime,
        "averageMediumTimeDifference", humanReadableMediumTime,
        "averageHighTimeDifference", humanReadableHighTime
    );
  }

  private static String getHumanReadableTime(double timeDifferenceMillis) {
    long totalSeconds = (long) (timeDifferenceMillis / 1000);

    long days = totalSeconds / (24 * 3600);
    totalSeconds %= (24 * 3600);

    long hours = totalSeconds / 3600;
    totalSeconds %= 3600;

    long minutes = totalSeconds / 60;
    long seconds = totalSeconds % 60;

    StringBuilder result = new StringBuilder();

    if (days > 0) {
        result.append(days).append(" day").append(days == 1 ? "" : "s");
    }

    if (hours > 0) {
        if (result.length() > 0) result.append(", ");
        result.append(hours).append(" hour").append(hours == 1 ? "" : "s");
    }

    if (minutes > 0) {
        if (result.length() > 0) result.append(", ");
        result.append(minutes).append(" minute").append(minutes == 1 ? "" : "s");
    }

    if (seconds > 0 || result.length() == 0) { // Mostrar segundos si es el único valor
        if (result.length() > 0) result.append(", ");
        result.append(seconds).append(" second").append(seconds == 1 ? "" : "s");
    }

    return result.toString();
  }
}
