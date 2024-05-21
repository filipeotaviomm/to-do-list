package com.todolist.todo.dtos;

import com.todolist.todo.models.TagModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagResponseDto {
  private Long id;
  private String name;

  public TagResponseDto(TagModel tag) {
    this.id = tag.getId();
    this.name = tag.getName();
  }
}
