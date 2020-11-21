package com.supensour.validation.beanvalidation.handler;

import com.supensour.validation.beanvalidation.model.exception.ValidationException;
import com.supensour.validation.beanvalidation.sample.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validation;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@ExtendWith(MockitoExtension.class)
class ValidatorImplTest {

  private ValidatorImpl validator;

  @BeforeEach
  public void setUp() {
    validator = new ValidatorImpl(Validation.buildDefaultValidatorFactory().getValidator());
  }

  @Test
  void validate_valid() {
    User user = User.builder()
        .id("id")
        .name("name")
        .build();
    assertDoesNotThrow(() -> {
      validator.validate(user);
    });
  }

  @Test
  void validate_invalid() {
    User user = User.builder()
        .build();
    ValidationException e = assertThrows(ValidationException.class, () -> {
      validator.validate(user);
    });
    assertEquals(2, e.getErrors().size());
    assertEquals(1, e.getErrors().get("id").size());
    assertEquals("Blank", e.getErrors().getFirst("id"));
    assertEquals(1, e.getErrors().get("name").size());
    assertEquals("Blank", e.getErrors().getFirst("name"));
  }

}
