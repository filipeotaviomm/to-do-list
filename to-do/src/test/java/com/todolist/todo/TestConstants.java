package com.todolist.todo;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.todolist.todo.models.ToDoModel;
import com.todolist.todo.models.ToDoModel.Priority;

public class TestConstants {
  public static final List<ToDoModel> TODOS = new ArrayList<>() {
    {

      LocalDateTime now = LocalDateTime.now();
      Timestamp createdAt = Timestamp.valueOf(now);
      Timestamp updatedAt = Timestamp.valueOf(now);

      add(new ToDoModel(9995L, "to do 1", "To do, to do", false, Priority.LOW, createdAt, updatedAt));
      add(new ToDoModel(9996L, "to do 2", "To do, to do", false, Priority.LOW, createdAt, updatedAt));
      add(new ToDoModel(9997L, "to do 3", "To do, to do", false, Priority.LOW, createdAt, updatedAt));
      add(new ToDoModel(9998L, "to do 4", "To do, to do", false, Priority.LOW, createdAt, updatedAt));
      add(new ToDoModel(9999L, "to do 5", "To do, to do", false, Priority.LOW, createdAt, updatedAt));
    }
  };

  public static final ToDoModel TODO = TODOS.get(0);
}
