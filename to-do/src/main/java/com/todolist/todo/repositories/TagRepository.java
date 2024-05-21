package com.todolist.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todolist.todo.models.TagModel;

@Repository
public interface TagRepository extends JpaRepository<TagModel, Long> {
}
