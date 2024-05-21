package com.todolist.todo.services;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todolist.todo.dtos.CreateUserRequestDto;
import com.todolist.todo.dtos.GetUserResponseDto;
import com.todolist.todo.dtos.UpdateUserRequestDto;
import com.todolist.todo.exceptions.BadRequestException;
import com.todolist.todo.exceptions.PermissionDeniedException;
import com.todolist.todo.models.UserModel;
import com.todolist.todo.models.UserModel.UserRole;
import com.todolist.todo.repositories.UserRepository;
import com.todolist.todo.utils.TokenUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper = new ModelMapper();
  private final TokenUtil tokenUtil;

  public GetUserResponseDto createUser(CreateUserRequestDto body) {

    Optional<UserModel> userByEmail = userRepository.findByEmail(body.email());
    if (userByEmail.isPresent()) {
      throw new BadRequestException("This email already exists");
    }

    Optional<UserModel> userByUsername = userRepository.findByUsername(body.username());
    if (userByUsername.isPresent()) {
      throw new BadRequestException("This username already exists");
    }

    var user = new UserModel();
    BeanUtils.copyProperties(body, user, "password");

    user.setRole(body.role() == null ? UserRole.USER : body.role());
    user.setPassword(passwordEncoder.encode(body.password()));

    userRepository.save(user);

    return modelMapper.map(user, GetUserResponseDto.class);
  }

  public List<GetUserResponseDto> getAllUsers() {

    var allUsers = userRepository.findAll();

    return allUsers.stream()
        .map(user -> modelMapper.map(user, GetUserResponseDto.class))
        .collect(Collectors.toList());
  }

  public GetUserResponseDto getUserById(UUID userId, String token) {

    Optional<UserModel> foundUser = userRepository.findById(userId);
    if (foundUser.isEmpty()) {
      throw new BadRequestException("This user does not exist");
    }

    UserModel userLogged = tokenUtil.getUserFromToken(token);
    if (foundUser.get() != userLogged && userLogged.getRole() == UserRole.USER) {
      throw new PermissionDeniedException("You can only access your own profile");
    }

    return modelMapper.map(foundUser.get(), GetUserResponseDto.class);
  }

  public GetUserResponseDto updateUser(UUID userId, UpdateUserRequestDto body, String token) {

    Optional<UserModel> foundUser = userRepository.findById(userId);
    var user = foundUser.get();

    if (foundUser.isEmpty()) {
      throw new BadRequestException("This user does not exist!");
    }

    UserModel userLogged = tokenUtil.getUserFromToken(token);
    if (foundUser.get() != userLogged && userLogged.getRole() == UserRole.USER) {
      throw new PermissionDeniedException("You can only update your own profile");
    }

    if (body.name() != null) {
      user.setName(body.name());
    }

    if (body.email() != null) {
      Optional<UserModel> userByEmail = userRepository.findByEmail(body.email());
      if (userByEmail.isPresent() && userByEmail.get().getId() != user.getId()) {
        throw new BadRequestException("This email already exists");
      }
      user.setEmail(body.email());
    }

    if (body.username() != null) {
      Optional<UserModel> userByUsername = userRepository.findByUsername(body.username());
      if (userByUsername.isPresent() && userByUsername.get().getId() != user.getId()) {
        throw new BadRequestException("This username already exists");
      }
      user.setUsername(body.username());
    }

    if (body.role() != null) {
      user.setRole(body.role());
    }

    if (body.password() != null) {
      user.setPassword(passwordEncoder.encode(body.password()));
    }

    userRepository.save(user);

    return modelMapper.map(user, GetUserResponseDto.class);
  }

  public void deleteUser(UUID id, String token) {
    Optional<UserModel> foundUser = userRepository.findById(id);
    if (foundUser.isEmpty()) {
      throw new BadRequestException("This user does not exist");
    }

    UserModel userLogged = tokenUtil.getUserFromToken(token);
    if (foundUser.get() != userLogged && userLogged.getRole() == UserRole.USER) {
      throw new PermissionDeniedException("You can only delete your own profile");
    }

    userRepository.delete(foundUser.get());
  }
}
