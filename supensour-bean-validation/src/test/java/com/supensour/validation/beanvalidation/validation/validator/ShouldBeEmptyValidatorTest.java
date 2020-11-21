package com.supensour.validation.beanvalidation.validation.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@ExtendWith(MockitoExtension.class)
class ShouldBeEmptyValidatorTest {

  @InjectMocks
  private ShouldBeEmptyValidator validator;

  @Test
  void isValid() {
    assertTrue(validator.isValid("", null));
    assertFalse(validator.isValid("  ", null));
    assertTrue(validator.isValid(null, null));
    assertFalse(validator.isValid("str", null));
  }

}
