package com.ead.authuser.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public ResponseEntity<List<UserModel>> getAllUsers() {
    return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") UUID id) {
    Optional<UserModel> userModelOptional = userService.findById(id);

    return userModelOptional.isPresent()
        ? ResponseEntity.ok().body(userModelOptional.get())
        : ResponseEntity.notFound().build();

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id) {
    Optional<UserModel> userModelOptional = userService.findById(id);

    if (!userModelOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    userService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
