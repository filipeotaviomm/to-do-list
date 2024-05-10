package com.todolist.todo.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.todolist.todo.dtos.CreateToDoDto;
import com.todolist.todo.dtos.GetToDoResponseDto;
import com.todolist.todo.dtos.UpdateToDoDto;
import com.todolist.todo.models.UserModel;
import com.todolist.todo.repositories.UserRepository;
import com.todolist.todo.services.ToDoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class ToDoController {
  private final ToDoService toDoService;
  private final UserRepository userRepository;
  @Value("${api.security.token.secret}")
  private String secretKey;

  @PostMapping
  ResponseEntity<GetToDoResponseDto> create(
      @Valid @RequestBody CreateToDoDto body,
      @RequestHeader("Authorization") String token) {

    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
        .build()
        .verify(token.replace("Bearer ", ""));

    String userId = decodedJWT.getSubject();

    Optional<UserModel> user = userRepository.findById(UUID.fromString(userId));

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(toDoService.create(body, user.get()));
  }

  @GetMapping("/all")
  List<GetToDoResponseDto> listAll() {
    return toDoService.listAll();
  }

  @GetMapping("/{id}")
  GetToDoResponseDto list(@PathVariable Long id) {
    return toDoService.list(id);
  }

  @PatchMapping("/{id}")
  GetToDoResponseDto update(@PathVariable Long id, @RequestBody UpdateToDoDto payload) {
    return toDoService.update(id, payload);
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable Long id) {
    toDoService.deleteToDo(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
