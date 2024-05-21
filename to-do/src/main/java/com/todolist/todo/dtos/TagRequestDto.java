package com.todolist.todo.dtos;

import jakarta.validation.constraints.*;

public record TagRequestDto(
    @NotNull String name) {
}
