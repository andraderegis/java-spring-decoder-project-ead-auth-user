package com.ead.authuser.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ead.authuser.constants.DateTimeConstants;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import lombok.Data;

// Lombok decorator. Avoid implement manual getters and setters
@Data
// All null attribute are hidden
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "USERS")
public class UserModel extends RepresentationModel<UserModel> implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false, unique = true, length = 50)
  private String username;

  @Column(nullable = false, unique = true, length = 50)
  private String email;

  @Column(nullable = false, length = 255)
  @JsonIgnore
  private String password;

  @Column(nullable = false, length = 150)
  private String fullName;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UserStatus status;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UserType type;

  @Column(length = 20)
  private String phoneNumber;

  @Column(length = 11)
  private String cpf;

  @Column
  private String imageUrl;

  @Column(nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeConstants.DATE_TIME_FORMAT)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeConstants.DATE_TIME_FORMAT)
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<UserCourseModel> usersCourses;
}
