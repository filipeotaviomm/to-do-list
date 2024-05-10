package com.todolist.todo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
@Getter
@Setter
public class UserModel {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "user_id")
  private UUID id;

  @NotBlank(message = "Name is mandatory")
  @Size(max = 20, message = "Name cannot be longer than 20 characters")
  private String name;

  @NotBlank(message = "Email is mandatory")
  @Size(max = 30, message = "Email cannot be longer than 30 characters")
  @Email(message = "Invalid email format")
  @Column(unique = true)
  private String email;

  @NotBlank(message = "Username is mandatory")
  @Size(max = 30, message = "Username cannot be longer than 30 characters")
  @Column(unique = true)
  private String username;

  @NotBlank(message = "Password is mandatory")
  private String password;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private List<ToDoModel> toDos;

  // @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  // @JoinTable(name = "tb_users_roles", joinColumns = @JoinColumn(name =
  // "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  // private Set<RoleModel> roles;

  public UserModel() {
  }

  public UserModel(UUID id, String name, String email, String username, String password, List<ToDoModel> toDos) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.username = username;
    this.password = password;
    this.toDos = toDos;
  }
}
