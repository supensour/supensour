package com.supensour.validation.beanvalidation.model.exception;

import com.supensour.core.model.exception.ErrorsException;
import com.supensour.core.utils.ErrorUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public class ValidationException extends ErrorsException {

  private static final long serialVersionUID = 5315694566999287064L;

  @Getter
  private final Set<ConstraintViolation<?>> constraintViolations = new HashSet<>();

  public ValidationException(Set<? extends ConstraintViolation<?>> violations) {
    this("Failed validation", violations);
  }

  public ValidationException(String message, Set<? extends ConstraintViolation<?>> violations) {
    super(message, HttpStatus.BAD_REQUEST, ErrorUtils.mapConstraintViolations(violations));
    Optional.ofNullable(violations)
        .ifPresent(constraintViolations::addAll);
  }

}
