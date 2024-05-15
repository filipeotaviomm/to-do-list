package com.todolist.todo.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.todo.dtos.CreateUserRequestDto;
import com.todolist.todo.dtos.GetUserResponseDto;
import com.todolist.todo.dtos.UpdateUserRequestDto;
import com.todolist.todo.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/register")
  ResponseEntity<GetUserResponseDto> create(
      @Valid @RequestBody CreateUserRequestDto body) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(userService.createUser(body));
  }

  @GetMapping("/all")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  List<GetUserResponseDto> getUser() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  GetUserResponseDto getUserById(@PathVariable UUID id, @RequestHeader("Authorization") String token) {
    return userService.getUserById(id, token);
  }

  @PatchMapping("/{id}")
  GetUserResponseDto updateUser(
      @PathVariable UUID id, @Valid @RequestBody UpdateUserRequestDto body,
      @RequestHeader("Authorization") String token) {
    return userService.updateUser(id, body, token);
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable UUID id, @RequestHeader("Authorization") String token) {
    userService.deleteUser(id, token);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
