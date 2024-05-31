package com.todolist.todo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_addresses")
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
  @Size(min = 8, max = 8, message = "Zip Code must have 8 characters")
  private String zipCode;

  @NotNull(message = "Number Code is mandatory")
  private String number;

  @NotBlank(message = "City is mandatory")
  @Size(max = 20, message = "City cannot be longer than 20 characters")
  private String city;

  @NotBlank(message = "State is mandatory")
  @Size(min = 2, max = 2, message = "City must have 2 characters")
  private String state;

  @OneToOne(mappedBy = "address")
  private UserModel user;
}