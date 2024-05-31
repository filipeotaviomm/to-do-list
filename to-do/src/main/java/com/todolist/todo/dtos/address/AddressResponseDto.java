package com.todolist.todo.dtos.address;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDto {
  Long id;
  String street;
  String number;
  String city;
  String state;
  String zipCode;
}
