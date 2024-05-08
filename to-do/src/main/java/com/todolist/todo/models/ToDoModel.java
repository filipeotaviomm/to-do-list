package com.todolist.todo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_todos")
public class ToDoModel {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
  private Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

  @Column(name = "updated_at", nullable = false, updatable = false)
  private Timestamp updatedAt = Timestamp.valueOf(LocalDateTime.now());

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
      Timestamp updatedAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.accomplished = accomplished;
    this.priority = priority;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public ToDoModel(String name, String description, boolean accomplished, Priority priority) {
    this.name = name;
    this.description = description;
    this.accomplished = accomplished;
    this.priority = priority;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean getAccomplished() {
    return accomplished;
  }

  public void setAccomplished(boolean accomplished) {
    this.accomplished = accomplished;
  }

  public Priority getPriority() {
    return priority;
  }

  public void setPriority(Priority priority) {
    this.priority = priority;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public Timestamp setCreatedAt() {
    return createdAt;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }
}
