package com.todolist.todo.dtos;

import jakarta.validation.constraints.*;
import com.todolist.todo.models.ToDoModel.Priority;

public record CreateToDoDto(@NotNull String name, @NotNull String description, @NotNull boolean accomplished,
    @NotNull Priority priority) {
}