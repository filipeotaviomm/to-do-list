package com.todolist.todo.dtos.address;

import jakarta.validation.constraints.*;

public record CreateAddressRequestDto(
                @NotNull String street,
                @NotNull String zipCode,
                @NotNull Integer number,
                @NotNull String city,
                @NotNull String state) {
}
