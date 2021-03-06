package com.supensour.validation.beanvalidation.validation.validator;

import com.supensour.validation.beanvalidation.validation.ShouldBeBlank;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public class ShouldBeBlankValidator implements ConstraintValidator<ShouldBeBlank, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return !StringUtils.hasText(value);
  }

}
