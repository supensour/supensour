package com.supensour.validation.beanvalidation.handler;

import com.supensour.validation.beanvalidation.Validator;
import com.supensour.validation.beanvalidation.model.exception.ValidationException;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public class ValidatorImpl implements Validator {

  private final javax.validation.Validator validator;

  public ValidatorImpl(javax.validation.Validator validator) {
    this.validator = validator;
  }

  @Override
  public <T> void validate(T request, Class<?>... groups) {
    Set<ConstraintViolation<T>> violations = validator.validate(request);
    if(!violations.isEmpty()) {
      throw new ValidationException(violations);
    }
  }

}
