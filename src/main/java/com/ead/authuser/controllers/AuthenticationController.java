package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

  @Autowired
  private UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<Object> register(@RequestBody UserDto userDto) {
    if (userService.existsByUsername(userDto.getUsername())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is already taken!");
    }

    if (userService.existsByEmail(userDto.getUsername())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already taken!");
    }

    var userModel = new UserModel();

    BeanUtils.copyProperties(userDto, userModel);

    userModel.setStatus(UserStatus.ACTIVE);

    userService.save(userModel);

    return ResponseEntity.status(HttpStatus.CREATED).body(userModel);

  }
}
