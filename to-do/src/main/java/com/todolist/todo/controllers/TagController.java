package com.todolist.todo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.todo.dtos.GetToDoTagResponseDto;
import com.todolist.todo.dtos.TagRequestDto;
import com.todolist.todo.dtos.TagResponseDto;
import com.todolist.todo.services.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TagController {
  private final TagService tagService;

  @PostMapping("/{id}/tag")
  ResponseEntity<GetToDoTagResponseDto> createAndAdd(
      @PathVariable Long id,
      @Valid @RequestBody TagRequestDto body,
      @RequestHeader("Authorization") String token) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(tagService.createTagAndAddToToDo(id, body, token));
  }

  @GetMapping("/tag/all")
  public List<TagResponseDto> listAll() {
    return tagService.listAllTags();
  }

  @GetMapping("/tag/{id}")
  public TagResponseDto listTag(@PathVariable Long id) {
    return tagService.listTagById(id);
  }

  @PutMapping("/tag/{id}")
  public TagResponseDto update(
      @PathVariable Long id,
      @Valid @RequestBody TagRequestDto body,
      @RequestHeader("Authorization") String token) {

    return tagService.updateTag(id, body, token);
  }

  @DeleteMapping("/{toDoId}/tag/{tagId}")
  ResponseEntity<GetToDoTagResponseDto> createAndAdd(
      @PathVariable Long toDoId,
      @PathVariable Long tagId,
      @RequestHeader("Authorization") String token) {

    return ResponseEntity.ok(tagService.removeTagFromToDo(toDoId, tagId, token));
  }
}