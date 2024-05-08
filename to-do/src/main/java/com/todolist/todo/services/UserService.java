package com.todolist.todo.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todolist.todo.dtos.CreateUserRequestDto;
import com.todolist.todo.dtos.GetUserResponseDto;

import com.todolist.todo.exceptions.BadRequestException;
import com.todolist.todo.models.UserModel;
import com.todolist.todo.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper = new ModelMapper();

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

    user.setPassword(passwordEncoder.encode(body.password()));

    userRepository.save(user);

    return modelMapper.map(user, GetUserResponseDto.class);
  }

  public GetUserResponseDto getUser() {

    var allUsers = userRepository.findAll();

    return modelMapper.map(allUsers, GetUserResponseDto.class);
  }

}
