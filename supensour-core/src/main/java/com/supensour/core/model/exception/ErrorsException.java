package com.supensour.core.model.exception;

import com.supensour.model.map.ListValueMap;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public class ErrorsException extends RuntimeException {

  private static final long serialVersionUID = 2179124296254798130L;

  @Getter
  private final HttpStatus status;

  @Getter
  private final ListValueMap<String, String> errors;

  public ErrorsException(String message, Throwable cause, HttpStatus status, ListValueMap<String, String> errors) {
    super(message, cause);
    this.errors = errors;
    this.status = Objects.requireNonNull(status);
  }

  public ErrorsException(String message, Throwable cause, HttpStatus status) {
    this(message, cause, status, null);
  }

  public ErrorsException(String message, HttpStatus status, ListValueMap<String, String> errors) {
    this(message, null, status, errors);
  }

  public ErrorsException(String message, HttpStatus status) {
    this(message, null, status, null);
  }

  public ErrorsException(Throwable cause, HttpStatus status, ListValueMap<String, String> errors) {
    this(null, cause, status, errors);
  }

  public ErrorsException(Throwable cause, HttpStatus status) {
    this(null, cause, status, null);
  }

  public ErrorsException(HttpStatus status, ListValueMap<String, String> errors) {
    this(null, null, status, errors);
  }

  public ErrorsException(HttpStatus status) {
    this(null, null, status, null);
  }

}
