package com.todolist.todo.dtos;

import jakarta.validation.constraints.*;

public record UpdateUserRequestDto(
        String name,
        String email,
        String username,
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be at least 8 characters long") String password) {
}
