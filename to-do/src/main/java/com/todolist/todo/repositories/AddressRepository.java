package com.todolist.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todolist.todo.models.AddressModel;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel, Long> {
}
