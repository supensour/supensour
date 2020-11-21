package com.supensour.validation.beanvalidation.validation.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@ExtendWith(MockitoExtension.class)
class ShouldBeBlankValidatorTest {

  @InjectMocks
  private ShouldBeBlankValidator validator;

  @Test
  void isValid() {
    assertTrue(validator.isValid("", null));
    assertTrue(validator.isValid("  ", null));
    assertTrue(validator.isValid(null, null));
    assertFalse(validator.isValid("str", null));
  }

}
