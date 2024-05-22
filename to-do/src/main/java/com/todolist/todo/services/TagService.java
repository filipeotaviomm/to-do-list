package com.todolist.todo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.todolist.todo.models.TagModel;
import com.todolist.todo.models.ToDoModel;
import com.todolist.todo.models.UserModel;
import com.todolist.todo.models.UserModel.UserRole;
import com.todolist.todo.dtos.tag.TagRequestDto;
import com.todolist.todo.dtos.tag.TagResponseDto;
import com.todolist.todo.dtos.tag.ToDoTagResponseDto;
import com.todolist.todo.exceptions.BadRequestException;
import com.todolist.todo.exceptions.PermissionDeniedException;
import com.todolist.todo.repositories.TagRepository;
import com.todolist.todo.repositories.ToDoRepository;
import com.todolist.todo.utils.TokenUtil;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;

@Service
@RequiredArgsConstructor
public class TagService {
  private final ToDoRepository toDoRepository;
  private final TagRepository tagRepository;
  private final TokenUtil tokenUtil;
  private final ModelMapper modelMapper = new ModelMapper();

  public ToDoTagResponseDto createTagAndAddToToDo(
      Long toDoId, TagRequestDto body, String token) {

    Optional<ToDoModel> foundToDo = toDoRepository.findById(toDoId);
    if (foundToDo.isEmpty()) {
      throw new BadRequestException("To do %d does not exist!".formatted(toDoId));
    }

    ToDoModel toDo = foundToDo.get();
    UserModel userLogged = tokenUtil.getUserFromToken(token);

    if (!toDo.getUser().getId().equals(userLogged.getId()) && userLogged.getRole() == UserRole.USER) {
      throw new PermissionDeniedException("You don't have permission to do it.");
    }

    String tagName = body.name().toLowerCase();

    List<TagModel> allTags = tagRepository.findAll();
    Optional<TagModel> filteredTag = allTags.stream().filter(
        tag -> tag.getName().equals(tagName)).findFirst();

    if (filteredTag.isPresent()) {
      TagModel existingTag = filteredTag.get();
      existingTag.setName(tagName);
      toDo.addTag(existingTag);
    } else {
      TagModel tag = new TagModel();
      tag.setName(tagName);
      tagRepository.save(tag);
      toDo.addTag(tag);
    }

    toDoRepository.save(toDo);

    return modelMapper.map(toDo, ToDoTagResponseDto.class);
  }

  public List<TagResponseDto> listAllTags() {

    List<TagModel> allTags = tagRepository.findAll();

    List<TagResponseDto> allTagsInDto = allTags.stream().map(
        tag -> modelMapper.map(tag, TagResponseDto.class))
        .collect(Collectors.toList());

    return allTagsInDto;
  }

  public TagResponseDto listTagById(Long tagId) {

    Optional<TagModel> tag = tagRepository.findById(tagId);
    if (tag.isEmpty()) {
      throw new BadRequestException("Tag %d does not exist!".formatted(tagId));
    }

    return modelMapper.map(tag.get(), TagResponseDto.class);
  }

  public TagResponseDto updateTag(Long tagId, TagRequestDto body, String token) {

    Optional<TagModel> foundTag = tagRepository.findById(tagId);
    if (foundTag.isEmpty()) {
      throw new BadRequestException("Tag %d does not exist!".formatted(tagId));
    }

    UserModel userLogged = tokenUtil.getUserFromToken(token);

    if (userLogged.getRole() == UserRole.USER) {
      throw new PermissionDeniedException("You don't have permission to do it.");
    }

    TagModel tag = foundTag.get();
    String tagName = body.name().toLowerCase();

    Optional<TagModel> existingTag = tagRepository.findByName(tagName);
    if (existingTag.isPresent()) {
      throw new BadRequestException("This tag already exist");
    }

    tag.setName(tagName);
    tagRepository.save(tag);

    List<ToDoModel> allToDos = toDoRepository.findAll();
    List<ToDoModel> filteredToDos = allToDos.stream()
        .filter(toDo -> toDo.getTags().stream().anyMatch(t -> t.getId().equals(tagId)))
        .collect(Collectors.toList());

    for (ToDoModel toDo : filteredToDos) {
      toDo.getTags().forEach(t -> {
        if (t.getId().equals(tagId)) {
          t.setName(tagName);
        }
      });
      toDoRepository.save(toDo);
    }

    return modelMapper.map(foundTag.get(), TagResponseDto.class);
  }

  public ToDoTagResponseDto removeTagFromToDo(Long toDoId, Long tagId, String token) {

    Optional<ToDoModel> foundToDo = toDoRepository.findById(toDoId);
    if (foundToDo.isEmpty()) {
      throw new BadRequestException("To do %d does not exist!".formatted(toDoId));
    }

    Optional<TagModel> foundTag = tagRepository.findById(tagId);
    if (foundTag.isEmpty()) {
      throw new BadRequestException("Tag %d does not exist!".formatted(tagId));
    }

    ToDoModel toDo = foundToDo.get();

    Boolean toDoHasTag = toDo.getTags().stream().anyMatch(t -> t.getId().equals(tagId));
    if (!toDoHasTag) {
      throw new BadRequestException("Tag %d does not exist in this to do".formatted(tagId));
    }

    TagModel tag = foundTag.get();

    UserModel userLogged = tokenUtil.getUserFromToken(token);

    if (!toDo.getUser().getId().equals(userLogged.getId()) && userLogged.getRole() == UserRole.USER) {
      throw new PermissionDeniedException("You don't have permission to do it.");
    }

    toDo.removeTag(tag);
    toDoRepository.save(toDo);

    return modelMapper.map(toDo, ToDoTagResponseDto.class);

  }
}
