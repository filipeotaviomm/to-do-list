package com.todolist.todo.dtos.tag;

import java.util.Set;
import java.util.stream.Collectors;

import com.todolist.todo.models.ToDoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToDoTagResponseDto {
  private Long id;
  private String name;
  private String description;
  private boolean accomplished;
  private ToDoModel.Priority priority;
  private Set<TagResponseDto> tags;

  public ToDoTagResponseDto(ToDoModel toDo) {
    this.id = toDo.getId();
    this.name = toDo.getName();
    this.description = toDo.getDescription();
    this.accomplished = toDo.isAccomplished();
    this.priority = toDo.getPriority();
    this.tags = toDo.getTags().stream().map(tag -> new TagResponseDto(tag)).collect(Collectors.toSet());
  }
}
