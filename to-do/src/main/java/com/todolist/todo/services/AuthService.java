package com.todolist.todo.services;

import com.todolist.todo.models.UserModel;
import com.todolist.todo.repositories.UserRepository;
import com.todolist.todo.dtos.login.LoginRequestDto;
import com.todolist.todo.dtos.login.LoginResponseDto;
import com.todolist.todo.exceptions.BadRequestException;
import com.todolist.todo.infra.security.TokenService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

  public LoginResponseDto login(LoginRequestDto body) {

    Optional<UserModel> foundUser = userRepository.findByEmail(body.email());

    if (foundUser.isEmpty()) {
      throw new BadRequestException("Invalid Credential");
    }

    var user = foundUser.get();

    if (!passwordEncoder.matches(body.password(), user.getPassword())) {
      throw new BadRequestException("Invalid Credential");
    }

    String token = this.tokenService.generateToken(user);
    return new LoginResponseDto(user.getName(), token);
  }
}
