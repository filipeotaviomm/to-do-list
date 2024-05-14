package com.todolist.todo.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.todolist.todo.models.ToDoModel;
import com.todolist.todo.models.UserModel;
import com.todolist.todo.dtos.CreateToDoDto;
import com.todolist.todo.dtos.GetToDoResponseDto;
import com.todolist.todo.dtos.UpdateToDoDto;
import com.todolist.todo.exceptions.BadRequestException;
import com.todolist.todo.repositories.ToDoRepository;
import com.todolist.todo.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
@RequiredArgsConstructor
public class ToDoService {
  private final ToDoRepository toDoRepository;
  private final UserRepository UserRepository;
  private final ModelMapper modelMapper = new ModelMapper();
  @Value("${api.security.token.secret}")
  private String secretKey;

  private UserModel getUserFromToken(String token) {

    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
        .build()
        .verify(token.replace("Bearer ", ""));

    String userId = decodedJWT.getSubject();

    Optional<UserModel> user = UserRepository.findById(UUID.fromString(userId));

    return user.get();
  }

  public GetToDoResponseDto createToDo(CreateToDoDto body, String token) {

    UserModel userLogged = this.getUserFromToken(token);

    var toDo = new ToDoModel();
    BeanUtils.copyProperties(body, toDo);
    toDo.setUser(userLogged);
    toDoRepository.save(toDo);

    GetToDoResponseDto toDoFormated = modelMapper.map(
        toDo, GetToDoResponseDto.class);

    return toDoFormated;
  }

  public List<GetToDoResponseDto> listAllToDos(String token) {

    UserModel userLogged = this.getUserFromToken(token);

    List<ToDoModel> allToDos = toDoRepository.findAll();

    List<GetToDoResponseDto> filteredToDos = allToDos.stream()
        .filter(toDo -> toDo.getUser().getId().equals(userLogged.getId()))
        .map(toDo -> modelMapper.map(toDo, GetToDoResponseDto.class))
        .collect(Collectors.toList());

    return filteredToDos;
  }

  public GetToDoResponseDto listToDo(Long id, String token) {

    Optional<ToDoModel> foundToDo = toDoRepository.findById(id);
    if (foundToDo.isEmpty()) {
      throw new BadRequestException("To do %d does not exist!".formatted(id));
    }

    UserModel userLogged = this.getUserFromToken(token);
    if (foundToDo.get().getUser() != userLogged) {
      throw new BadRequestException("You can only list your to dos");
    }

    GetToDoResponseDto toDoFormated = modelMapper.map(
        foundToDo, GetToDoResponseDto.class);

    return toDoFormated;
  }

  public GetToDoResponseDto updateToDo(Long id, UpdateToDoDto body, String token) {

    Optional<ToDoModel> foundToDo = toDoRepository.findById(id);
    if (foundToDo.isEmpty()) {
      throw new BadRequestException("To do %d does not exist!".formatted(id));
    }

    UserModel userLogged = this.getUserFromToken(token);
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

    GetToDoResponseDto toDoFormated = modelMapper.map(
        toDo, GetToDoResponseDto.class);

    return toDoFormated;
  }

  public void deleteToDo(Long id, String token) {
    Optional<ToDoModel> foundToDo = toDoRepository.findById(id);
    if (foundToDo.isEmpty()) {
      throw new BadRequestException("This user does not exist");
    }

    UserModel userLogged = this.getUserFromToken(token);
    if (foundToDo.get().getUser() != userLogged) {
      throw new BadRequestException("You can only update your to dos");
    }

    toDoRepository.delete(foundToDo.get());
  }
}
