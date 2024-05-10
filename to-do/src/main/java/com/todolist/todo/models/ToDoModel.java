package com.todolist.todo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "tb_todos")
@Getter
@Setter
public class ToDoModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Name is mandatory")
  @Size(max = 255, message = "Name cannot be longer than 255 characters")
  private String name;

  @NotBlank(message = "Description is mandatory")
  @Size(max = 1000, message = "Description cannot be longer than 1000 characters")
  private String description;

  @Column(nullable = false)
  private boolean accomplished;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Priority priority;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Timestamp createdAt;

  @Column(name = "updated_at", nullable = false)
  private Timestamp updatedAt;

  public enum Priority {
    LOW,
    MEDIUM,
    HIGH
  }

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserModel user;

  public ToDoModel() {

  }

  public ToDoModel(
      Long id, String name, String description, boolean accomplished, Priority priority, Timestamp createdAt,
      Timestamp updatedAt, UserModel user) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.accomplished = accomplished;
    this.priority = priority;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.user = user;
  }
}
