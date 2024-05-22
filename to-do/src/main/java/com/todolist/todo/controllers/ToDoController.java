package com.todolist.todo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.todo.dtos.toDo.CreateToDoRequestDto;
import com.todolist.todo.dtos.toDo.ToDoResponseDto;
import com.todolist.todo.dtos.toDo.UpdateToDoRequestDto;
import com.todolist.todo.services.ToDoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class ToDoController {
  private final ToDoService toDoService;
  private static final Logger logger = LoggerFactory.getLogger(ToDoController.class);

  @PostMapping
  ResponseEntity<ToDoResponseDto> create(
      @Valid @RequestBody CreateToDoRequestDto body,
      @RequestHeader("Authorization") String token) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(toDoService.createToDo(body, token));
  }

  @GetMapping("/all")
  List<ToDoResponseDto> listAll(
      @RequestHeader("Authorization") String token) {

    return toDoService.listAllToDos(token);
  }

  @GetMapping("/{id}")
  ToDoResponseDto list(
      @PathVariable Long id, @RequestHeader("Authorization") String token) {
    return toDoService.listToDoById(id, token);
  }

  @PatchMapping("/{id}")
  ToDoResponseDto update(
      @PathVariable Long id,
      @Valid @RequestBody UpdateToDoRequestDto body,
      @RequestHeader("Authorization") String token) {
    return toDoService.updateToDo(id, body, token);
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(
      @PathVariable Long id, @RequestHeader("Authorization") String token) {
    toDoService.deleteToDo(id, token);
    logger.info("Deleted task with ID: {}", id); // debug
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
