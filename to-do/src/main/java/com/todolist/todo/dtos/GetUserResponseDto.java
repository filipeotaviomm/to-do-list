package com.todolist.todo.dtos;

import java.util.UUID;

import com.todolist.todo.models.UserModel.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponseDto {
        UUID id;
        String name;
        String email;
        String username;
        UserRole role;
}
