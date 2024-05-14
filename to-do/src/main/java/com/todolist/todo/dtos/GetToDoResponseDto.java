package com.todolist.todo.dtos;

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
  GetUserResponseDto user;
}
