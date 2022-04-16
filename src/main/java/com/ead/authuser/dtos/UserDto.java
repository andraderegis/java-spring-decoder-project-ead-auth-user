package com.ead.authuser.dtos;

import java.util.UUID;

import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

  public interface UserView {
    public static interface RegistragionPost {
    }

    public static interface UserPut {
    }

    public static interface PasswordPut {
    }

    public static interface ImagePut {
    }
  }

  private UUID id;

  // Usando o JsonView, podemos usar o mesmo DTO, porém com visões diferentes.
  // Essas visões foram definidas por meio das interfaces UserView e suas filhas
  @JsonView(UserView.RegistragionPost.class)
  private String username;

  @JsonView(UserView.RegistragionPost.class)
  private String email;

  @JsonView({ UserView.RegistragionPost.class, UserView.PasswordPut.class })
  private String password;

  @JsonView(UserView.PasswordPut.class)
  private String oldPassword;

  @JsonView(UserView.RegistragionPost.class)
  private UserType type;

  @JsonView({ UserView.RegistragionPost.class, UserView.UserPut.class })
  private String fullName;

  @JsonView({ UserView.RegistragionPost.class, UserView.UserPut.class })
  private String phoneNumber;

  @JsonView({ UserView.RegistragionPost.class, UserView.UserPut.class })
  private String cpf;

  @JsonView(UserView.ImagePut.class)
  private String imageUrl;
}
