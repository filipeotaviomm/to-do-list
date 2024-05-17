package com.todolist.todo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAddressResponseDto {
  Long id;
  String street;
  Integer number;
  String city;
  String state;
  String zipCode;
}
