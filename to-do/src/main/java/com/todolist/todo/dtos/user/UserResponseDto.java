package com.todolist.todo.dtos.user;

import java.util.UUID;

import com.todolist.todo.models.ToDoModel;
import com.todolist.todo.models.UserModel.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
        private UUID id;
        private String name;
        private String email;
        private String username;
        private UserRole role;

        public UserResponseDto(ToDoModel toDo) {
                this.id = toDo.getUser().getId();
                this.name = toDo.getUser().getName();
                this.email = toDo.getUser().getEmail();
                this.username = toDo.getUser().getUsername();
                this.role = toDo.getUser().getRole();
        }
}
