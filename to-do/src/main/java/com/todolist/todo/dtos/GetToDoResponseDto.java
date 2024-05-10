package com.todolist.todo.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetToDoResponseDto {
  Long id;
  String name;
  String description;
  Boolean accomplished;
  String priority;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
  GetUserResponseDto user;
}
