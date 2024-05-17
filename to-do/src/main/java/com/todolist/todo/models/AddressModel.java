package com.todolist.todo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "address_id")
  private Long id;

  @NotBlank(message = "Street is mandatory")
  @Size(max = 50, message = "Street cannot be longer than 50 characters")
  private String street;

  @NotBlank(message = "Zip Code is mandatory")
  @Size(max = 8, message = "Zip Code cannot be longer than 8 characters")
  private String zipCode;

  @NotNull(message = "Number Code is mandatory")
  private Integer number;

  @NotBlank(message = "City is mandatory")
  @Size(max = 20, message = "City cannot be longer than 20 characters")
  private String city;

  @NotBlank(message = "State is mandatory")
  @Size(max = 2, message = "City cannot be longer than 2 characters")
  private String state;

  @OneToOne(mappedBy = "address")
  private UserModel user;
}