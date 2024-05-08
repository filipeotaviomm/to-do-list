package com.todolist.todo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.todo.dtos.CreateToDoDto;
import com.todolist.todo.models.ToDoModel;
import com.todolist.todo.services.ToDoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/todos")
public class ToDoController {
  @Autowired
  private ToDoService toDoService;

  @PostMapping
  ResponseEntity<ToDoModel> create(@Valid @RequestBody CreateToDoDto payload) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(toDoService.create(payload));
  }

  @GetMapping
  List<ToDoModel> listAll() {
    return toDoService.listAll();
  }

  @GetMapping("/{id}")
  ToDoModel list(@PathVariable Long id) {
    return toDoService.list(id);
  }

  @PutMapping("/{id}")
  ToDoModel update(@PathVariable Long id, @RequestBody CreateToDoDto payload) {
    return toDoService.update(id, payload);
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable Long id) {
    toDoService.delete(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
