package com.ead.authuser.repositories;

import java.util.UUID;

import com.ead.authuser.models.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, UUID> {

}
