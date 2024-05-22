package com.todolist.todo.dtos.tag;

import jakarta.validation.constraints.*;

public record TagRequestDto(
        @NotNull String name) {
}
