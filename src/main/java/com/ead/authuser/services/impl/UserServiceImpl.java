package com.ead.authuser.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  @Override
  public void save(UserModel userModel) {
    this.userRepository.save(userModel);
  }

  @Override
  public boolean existsByUsername(String username) {
    return this.userRepository.existsByUsername(username);
  }

  @Override
  public boolean existsByEmail(String email) {
    return this.userRepository.existsByEmail(email);
  }

  @Override
  public Page<UserModel> findAll(Pageable pageable) {
    return this.userRepository.findAll(pageable);
  }
}
