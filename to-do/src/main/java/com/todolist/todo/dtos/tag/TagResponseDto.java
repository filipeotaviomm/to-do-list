package com.todolist.todo.dtos.tag;

import com.todolist.todo.models.TagModel;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
