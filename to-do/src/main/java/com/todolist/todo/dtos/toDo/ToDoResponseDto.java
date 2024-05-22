package com.todolist.todo.dtos.toDo;

import java.util.Set;
import java.util.stream.Collectors;

import com.todolist.todo.dtos.tag.TagResponseDto;
import com.todolist.todo.dtos.user.UserResponseDto;
import com.todolist.todo.models.ToDoModel;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToDoResponseDto {
  private Long id;
  private String name;
  private String description;
  private Boolean accomplished;
  private ToDoModel.Priority priority;
  private Set<TagResponseDto> tags;
  private UserResponseDto user;

  public ToDoResponseDto(ToDoModel toDo) {
    this.id = toDo.getId();
    this.name = toDo.getName();
    this.description = toDo.getDescription();
    this.accomplished = toDo.isAccomplished();
    this.priority = toDo.getPriority();
    this.tags = toDo.getTags().stream().map(tag -> new TagResponseDto(tag)).collect(Collectors.toSet());
    this.user = new UserResponseDto(toDo);
  }
}
