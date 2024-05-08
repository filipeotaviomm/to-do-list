package com.todolist.todo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.todolist.todo.dtos.CreateUserRequestDto;
import com.todolist.todo.dtos.GetUserResponseDto;
import com.todolist.todo.models.UserModel;
import com.todolist.todo.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/register")
  ResponseEntity<GetUserResponseDto> create(@Valid @RequestBody CreateUserRequestDto body) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(userService.createUser(body));
  }

  @GetMapping("/all")
  ResponseEntity<GetUserResponseDto> get() {
    return ResponseEntity.status(HttpStatus.OK).body()
  }

}
