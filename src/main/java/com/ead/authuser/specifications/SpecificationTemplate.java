package com.ead.authuser.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.ead.authuser.models.UserModel;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

public class SpecificationTemplate {

  @And({
      @Spec(path = "type", spec = Equal.class),
      @Spec(path = "email", spec = Like.class),
      @Spec(path = "status", spec = Equal.class)
  })
  public interface UserSpec extends Specification<UserModel> {
  }
}
