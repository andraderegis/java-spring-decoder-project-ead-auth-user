package com.ead.authuser.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public List<UserModel> findAll() {
    return this.userRepository.findAll();
  }

  @Override
  public Optional<UserModel> findById(UUID userId) {
    return this.userRepository.findById(userId);
  }

  @Override
  public void delete(UUID id) {
    this.userRepository.deleteById(id);
  }
}
