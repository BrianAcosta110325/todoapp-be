package com.encora.utils;

import java.time.LocalDate;

public class DeclarationUtils {
  public static int getPriorityValue(Priority priority) {
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

  public static Integer getDueDateProximity(LocalDate DueDate) {
    if(DueDate == null) {
      return 0;
    }

    LocalDate now = LocalDate.now();
    int daysDifference = (int) java.time.Duration.between(now.atStartOfDay(), DueDate.atStartOfDay()).toDays();

    if (daysDifference < 7) {
      return 1;
    } else if (daysDifference < 14) {
      return 2;
    } else {
      return 3;
    }
  }
}
