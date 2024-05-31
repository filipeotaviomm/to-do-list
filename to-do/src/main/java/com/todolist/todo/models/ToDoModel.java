package com.todolist.todo.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_todos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToDoModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "todo_id")
  private Long id;

  @NotBlank(message = "Name is mandatory")
  @Size(max = 30, message = "Name cannot be longer than 30 characters")
  private String name;

  @NotBlank(message = "Description is mandatory")
  @Size(max = 1000, message = "Description cannot be longer than 1000 characters")
  private String description;

  @Column(nullable = false)
  private boolean accomplished;

  @Enumerated(EnumType.STRING)
  private Priority priority;

  public enum Priority {
    LOW,
    MEDIUM,
    HIGH
  }

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserModel user;

  @ManyToMany
  @JoinTable(name = "tb_todo_tag", joinColumns = @JoinColumn(name = "todo_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<TagModel> tags = new HashSet<>();

  public void addTag(TagModel tag) {
    this.tags.add(tag);
    tag.getToDos().add(this);
  }

  public void removeTag(TagModel tag) {
    this.tags.remove(tag);
    tag.getToDos().remove(this);
  }
}
