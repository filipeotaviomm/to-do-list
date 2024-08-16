package com.todolist.todo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.todo.dtos.address.AddressResponseDto;
import com.todolist.todo.dtos.address.CreateAddressRequestDto;
import com.todolist.todo.services.AddressService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AddressController {
  private final AddressService addressService;

  @PostMapping("/address")
  ResponseEntity<AddressResponseDto> create(
      @Valid @RequestBody CreateAddressRequestDto body, @RequestHeader("Authorization") String token) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(addressService.createAddress(body, token));
  }

  @GetMapping("/address/current")
  AddressResponseDto get(@RequestHeader("Authorization") String token) {
    return addressService.getAddress(token);
  }

  @GetMapping("/address/all")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  List<AddressResponseDto> getAll(@RequestHeader("Authorization") String token) {
    return addressService.getAllAddresses(token);
  }

  @PutMapping("/address/{id}")
  AddressResponseDto update(
      @PathVariable Long id, @Valid @RequestBody CreateAddressRequestDto body,
      @RequestHeader("Authorization") String token) {
    return addressService.updateAdress(id, body, token);
  }

  @DeleteMapping("/address/{id}")
  ResponseEntity<Void> delete(@PathVariable Long id, @RequestHeader("Authorization") String token) {
    addressService.deleteAddress(id, token);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
