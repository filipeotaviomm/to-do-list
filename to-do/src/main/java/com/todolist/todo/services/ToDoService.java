package com.todolist.todo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.todolist.todo.models.ToDoModel;
import com.todolist.todo.models.UserModel;
import com.todolist.todo.dtos.toDo.CreateToDoRequestDto;
import com.todolist.todo.dtos.toDo.ToDoResponseDto;
import com.todolist.todo.dtos.toDo.UpdateToDoRequestDto;
import com.todolist.todo.exceptions.BadRequestException;
import com.todolist.todo.repositories.ToDoRepository;
import com.todolist.todo.utils.TokenUtil;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

@Service
@RequiredArgsConstructor
public class ToDoService {
  private final ToDoRepository toDoRepository;
  private final ModelMapper modelMapper = new ModelMapper();
  private final TokenUtil tokenUtil;

  public ToDoResponseDto createToDo(CreateToDoRequestDto body, String token) {

    UserModel userLogged = tokenUtil.getUserFromToken(token);

    var toDo = new ToDoModel();
    BeanUtils.copyProperties(body, toDo);
    toDo.setUser(userLogged);
    toDoRepository.save(toDo);

    ToDoResponseDto toDoFormated = modelMapper.map(
        toDo, ToDoResponseDto.class);

    return toDoFormated;
  }

  public List<ToDoResponseDto> listAllToDos(String token) {

    UserModel userLogged = tokenUtil.getUserFromToken(token);

    List<ToDoModel> allToDos = toDoRepository.findAll();

    List<ToDoResponseDto> filteredToDos = allToDos.stream()
        .filter(toDo -> toDo.getUser().getId().equals(userLogged.getId()))
        .map(toDo -> modelMapper.map(toDo, ToDoResponseDto.class))
        .collect(Collectors.toList());

    return filteredToDos;
  }

  public ToDoResponseDto listToDoById(Long id, String token) {

    Optional<ToDoModel> foundToDo = toDoRepository.findById(id);
    if (foundToDo.isEmpty()) {
      throw new BadRequestException("To do %d does not exist!".formatted(id));
    }

    UserModel userLogged = tokenUtil.getUserFromToken(token);
    if (foundToDo.get().getUser() != userLogged) {
      throw new BadRequestException("You can only list your to dos");
    }

    ToDoResponseDto toDoFormated = modelMapper.map(
        foundToDo, ToDoResponseDto.class);

    return toDoFormated;
  }

  public ToDoResponseDto updateToDo(Long id, UpdateToDoRequestDto body, String token) {

    Optional<ToDoModel> foundToDo = toDoRepository.findById(id);
    if (foundToDo.isEmpty()) {
      throw new BadRequestException("To do %d does not exist!".formatted(id));
    }

    UserModel userLogged = tokenUtil.getUserFromToken(token);
    if (foundToDo.get().getUser() != userLogged) {
      throw new BadRequestException("You can only update your to dos");
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

    toDoRepository.save(toDo);

    ToDoResponseDto toDoFormated = modelMapper.map(
        toDo, ToDoResponseDto.class);

    return toDoFormated;
  }

  public void deleteToDo(Long id, String token) {
    Optional<ToDoModel> foundToDo = toDoRepository.findById(id);
    if (foundToDo.isEmpty()) {
      throw new BadRequestException("This user does not exist");
    }

    UserModel userLogged = tokenUtil.getUserFromToken(token);
    if (foundToDo.get().getUser() != userLogged) {
      throw new BadRequestException("You can only delete your to dos");
    }

    toDoRepository.delete(foundToDo.get());
  }
}
