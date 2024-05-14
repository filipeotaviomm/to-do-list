package com.todolist.todo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todolist.todo.models.ToDoModel;

@Repository
public interface ToDoRepository extends JpaRepository<ToDoModel, Long> {
  Optional<ToDoModel> findById(Long id);
}
