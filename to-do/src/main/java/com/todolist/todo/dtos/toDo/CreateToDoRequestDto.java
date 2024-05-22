package com.todolist.todo.dtos.toDo;

import jakarta.validation.constraints.*;
import com.todolist.todo.models.ToDoModel.Priority;

public record CreateToDoRequestDto(
                @NotNull String name,
                @NotNull String description,
                @NotNull Boolean accomplished,
                @NotNull Priority priority) {
}