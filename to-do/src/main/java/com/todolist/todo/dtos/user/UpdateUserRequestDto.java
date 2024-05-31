package com.todolist.todo.dtos.user;

import com.todolist.todo.models.UserModel.UserRole;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;

public record UpdateUserRequestDto(
        @Nullable String profileImageUrl,
        @Nullable String name,
        @Nullable String email,
        @Nullable String username,
        @Nullable UserRole role,
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be at least 8 characters long") @Nullable String password) {
}
