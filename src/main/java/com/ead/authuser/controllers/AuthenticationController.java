package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

  @Autowired
  private UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<Object> register(
      @RequestBody @Validated(UserDto.UserView.RegistrationPost.class) @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) {
    log.debug("POST register userDto received {}", userDto.toString());

    if (userService.existsByUsername(userDto.getUsername())) {
      log.warn("Username {} already exists", userDto.getUsername());

      return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is already taken!");
    }

    if (userService.existsByEmail(userDto.getUsername())) {
      log.warn("Email {} already exists", userDto.getUsername());

      return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already taken!");
    }

    var userModel = new UserModel();

    BeanUtils.copyProperties(userDto, userModel);

    userModel.setType(UserType.STUDENT);
    userModel.setStatus(UserStatus.ACTIVE);

    userService.save(userModel);

    log.debug("POST register userModel saved {}", userModel.toString());
    log.info("User saved successfully! - userId {}", userModel.getId());

    return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
  }

  @GetMapping("/")
  public String index() {
    log.trace("TRACE");
    log.debug("DEBUG");
    log.info("INFO");
    log.warn("WARN");
    log.error("ERROR");

    return "Logging Spring Boot...";
  }
}
