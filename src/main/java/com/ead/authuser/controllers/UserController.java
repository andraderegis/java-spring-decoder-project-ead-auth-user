package com.ead.authuser.controllers;

import java.util.Optional;
import java.util.UUID;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public ResponseEntity<Page<UserModel>> getAllUsers(
      SpecificationTemplate.UserSpec spec,
      @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
    Page<UserModel> userModelPage = userService.findAll(spec, pageable);

    if (!userModelPage.isEmpty()) {
      for (UserModel user : userModelPage.toList()) {
        user.add(linkTo(methodOn(UserController.class).getOne(user.getId())).withSelfRel());
      }
    }

    return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID id) {
    Optional<UserModel> userModelOptional = userService.findById(id);

    return userModelOptional.isPresent()
        ? ResponseEntity.ok().body(userModelOptional.get())
        : ResponseEntity.notFound().build();

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
    log.debug("DELETE deleteUser userId received {}", id);

    Optional<UserModel> userModelOptional = userService.findById(id);

    if (!userModelOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    userService.delete(id);

    log.debug("DELETE deleteUser userId {}", id);
    log.info("User deleted successfully! - userId {}", id);

    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id,
      @RequestBody @Validated(UserDto.UserView.UserPut.class) @JsonView(UserDto.UserView.UserPut.class) UserDto userDto) {

    log.debug("PUT updateUser userDto received {}", userDto.toString());

    Optional<UserModel> userModelOptional = userService.findById(id);

    if (!userModelOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    var userModel = userModelOptional.get();

    userModel.setFullName(userDto.getFullName());
    userModel.setPhoneNumber(userDto.getPhoneNumber());
    userModel.setCpf(userDto.getCpf());

    userService.save(userModel);

    log.debug("PUT register userId updated {}", userModel.getId());
    log.info("User updated successfully! - userId {}", userModel.getId());

    return ResponseEntity.ok().body(userModel);
  }

  @PutMapping("/{id}/password")
  public ResponseEntity<Object> updatePassword(@PathVariable(value = "id") UUID id,
      @RequestBody @Validated(UserDto.UserView.PasswordPut.class) @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto) {
    Optional<UserModel> userModelOptional = userService.findById(id);
    log.debug("PUT updatePassword userDto received {} ", userDto.toString());

    if (!userModelOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    if (!userModelOptional.get().getPassword().equals(userDto.getOldPassword())) {
      log.warn("Mismatched old password userId {}", userDto.getId());
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched old password.");
    }

    var userModel = userModelOptional.get();

    userModel.setPassword(userDto.getPassword());

    userService.save(userModel);

    log.debug("PUT updatePassword userId saved {} ", userModel.getId());
    log.info("Password updated successfully userId {} ", userModel.getId());

    return ResponseEntity.ok().body("Password updated successfully.");
  }

  @PutMapping("/{id}/image")
  public ResponseEntity<Object> updateImage(@PathVariable(value = "id") UUID id,
      @RequestBody @Validated(UserDto.UserView.ImagePut.class) @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto) {
    Optional<UserModel> userModelOptional = userService.findById(id);
    log.debug("PUT updateImage userDto received {} ", userDto.toString());

    if (!userModelOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    var userModel = userModelOptional.get();

    userModel.setImageUrl(userDto.getImageUrl());

    userService.save(userModel);

    log.debug("PUT updateImage userId saved {} ", userModel.getId());
    log.info("Image updated successfully userId {} ", userModel.getId());

    return ResponseEntity.ok().body("Image updated successfully.");
  }
}
