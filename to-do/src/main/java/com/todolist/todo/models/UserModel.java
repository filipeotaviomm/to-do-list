package com.todolist.todo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.UUID;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "tb_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserModel implements UserDetails {
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

  @Enumerated(EnumType.STRING)
  private UserRole role;

  public enum UserRole {
    ADMIN,
    USER
  }

  @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private List<ToDoModel> toDos;

  // @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  // @JoinTable(name = "tb_users_roles", joinColumns = @JoinColumn(name =
  // "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  // private Set<RoleModel> roles;

  // todo esse código abaixo é responsável pelo roles
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (this.role == UserRole.ADMIN)
      return List.of(
          new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
    else
      return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
