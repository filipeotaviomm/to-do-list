package com.todolist.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todolist.todo.models.AddressModel;
import com.todolist.todo.models.UserModel;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
  Optional<UserModel> findById(UUID id);

  Optional<UserModel> findByEmail(String email);

  Optional<UserModel> findByUsername(String username);

  Optional<UserModel> findByAddress(AddressModel address);

}
