package com.todolist.todo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.todolist.todo.models.ToDoModel;
import com.todolist.todo.dtos.CreateToDoDto;
import com.todolist.todo.exceptions.BadRequestException;
import com.todolist.todo.repositories.ToDoRepository;
import org.springframework.beans.BeanUtils;
import java.time.LocalDateTime;
import java.sql.Timestamp;

@Service
public class ToDoService {
  private ToDoRepository toDoRepository;

  public ToDoService(ToDoRepository toDoRepository) {
    this.toDoRepository = toDoRepository;
  }

  public ToDoModel create(CreateToDoDto payload) {
    var toDo = new ToDoModel();
    BeanUtils.copyProperties(payload, toDo);
    toDoRepository.save(toDo);
    return toDo;
  }

  public List<ToDoModel> listAll() {
    Sort sort = Sort.by(Direction.DESC, "priority")
        .and(Sort.by(Direction.ASC, "id"));

    return toDoRepository.findAll(sort);
  }

  public ToDoModel list(Long id) {
    Optional<ToDoModel> foundToDo = toDoRepository.findById(id);

    if (foundToDo.isEmpty()) {
      throw new BadRequestException("To do %d does not exist!".formatted(id));
    } else {
      return foundToDo.get();
    }
  }

  public ToDoModel update(Long id, CreateToDoDto payload) {
    Optional<ToDoModel> foundToDo = toDoRepository.findById(id);
    if (foundToDo.isEmpty()) {
      throw new BadRequestException("To do %d does not exist!".formatted(id));
    }
    var toDo = foundToDo.get();

    LocalDateTime now = LocalDateTime.now();
    Timestamp updatedAt = Timestamp.valueOf(now);
    toDo.setUpdatedAt(updatedAt);

    BeanUtils.copyProperties(payload, toDo);
    toDoRepository.save(toDo);
    return toDo;
  }

  public void delete(Long id) {
    Optional<ToDoModel> foundToDo = toDoRepository.findById(id);
    if (foundToDo.isEmpty()) {
      throw new BadRequestException("To do %d does not exist!".formatted(id));
    }
    toDoRepository.delete(foundToDo.get());
  }
}
