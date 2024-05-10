package com.todolist.todo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.todolist.todo.models.ToDoModel;
import com.todolist.todo.models.UserModel;
import com.todolist.todo.dtos.CreateToDoDto;
import com.todolist.todo.dtos.GetToDoResponseDto;
import com.todolist.todo.dtos.UpdateToDoDto;
import com.todolist.todo.exceptions.BadRequestException;
import com.todolist.todo.repositories.ToDoRepository;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import java.time.LocalDateTime;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class ToDoService {
  private final ToDoRepository toDoRepository;
  private final ModelMapper modelMapper = new ModelMapper();

  public GetToDoResponseDto create(CreateToDoDto body, UserModel user) {

    var toDo = new ToDoModel();
    BeanUtils.copyProperties(body, toDo);
    toDo.setUser(user);
    toDo.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
    toDo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
    toDoRepository.save(toDo);

    GetToDoResponseDto toDoFormated = modelMapper.map(
        toDo, GetToDoResponseDto.class);

    return toDoFormated;
  }

  public List<GetToDoResponseDto> listAll() {

    List<ToDoModel> allToDos = toDoRepository.findAll();

    return allToDos.stream()
        .map(user -> modelMapper.map(user, GetToDoResponseDto.class))
        .collect(Collectors.toList());
  }

  public GetToDoResponseDto list(Long id) {
    Optional<ToDoModel> foundToDo = toDoRepository.findById(id);

    if (foundToDo.isEmpty()) {
      throw new BadRequestException("To do %d does not exist!".formatted(id));
    }

    GetToDoResponseDto toDoFormated = modelMapper.map(
        foundToDo, GetToDoResponseDto.class);

    return toDoFormated;
  }

  public GetToDoResponseDto update(Long id, UpdateToDoDto body) {

    Optional<ToDoModel> foundToDo = toDoRepository.findById(id);
    if (foundToDo.isEmpty()) {
      throw new BadRequestException("To do %d does not exist!".formatted(id));
    }

    var toDo = foundToDo.get();

    if (body.name() != null) {
      toDo.setName(body.name());
    }

    if (body.description() != null) {
      toDo.setDescription(body.description());
    }

    if (body.accomplished()) {
      toDo.setAccomplished(body.accomplished());
    }

    if (body.priority() != null) {
      toDo.setPriority(body.priority());
    }

    toDo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

    toDoRepository.save(toDo);

    GetToDoResponseDto toDoFormated = modelMapper.map(
        toDo, GetToDoResponseDto.class);

    return toDoFormated;
  }

  public void deleteToDo(Long id) {
    Optional<ToDoModel> foundToDo = toDoRepository.findById(id);
    if (foundToDo.isEmpty()) {
      throw new BadRequestException("This user does not exist");
    }
    toDoRepository.delete(foundToDo.get());
  }
}
