package com.todolist.todo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.todolist.todo.models.UserModel;
import com.todolist.todo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenUtil {

  private final UserRepository userRepository;

  @Value("${api.security.token.secret}")
  private String secretKey;

  public UserModel getUserFromToken(String token) {
    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
        .build()
        .verify(token.replace("Bearer ", ""));

    String userId = decodedJWT.getSubject();

    Optional<UserModel> user = userRepository.findById(UUID.fromString(userId));

    return user.get();
  }
}
