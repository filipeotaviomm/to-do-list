package com.todolist.todo.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tag_id")
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "tags")
  private Set<ToDoModel> toDos = new HashSet<>();
}
