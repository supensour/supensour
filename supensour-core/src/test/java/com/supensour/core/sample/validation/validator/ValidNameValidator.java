package com.supensour.core.sample.validation.validator;

import com.supensour.core.sample.dto.Name;
import com.supensour.core.sample.validation.ValidName;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public class ValidNameValidator implements ConstraintValidator<ValidName, Name> {

  @Override
  public boolean isValid(Name name, ConstraintValidatorContext context) {
    if(name == null) {
      return true;
    }
    return StringUtils.hasText(name.getFirstName()) && StringUtils.hasText(name.getLastName());
  }

}
