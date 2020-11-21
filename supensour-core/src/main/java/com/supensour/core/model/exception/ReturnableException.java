package com.supensour.core.model.exception;

import com.supensour.core.utils.ResponseUtils;
import com.supensour.model.map.ListValueMap;
import com.supensour.model.web.Response;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public class ReturnableException extends RuntimeException {

  private static final long serialVersionUID = -7055426232483477828L;

  @Getter
  private final Response<?> response;

  public ReturnableException(String message, Throwable cause, Response<?> response) {
    super(message, cause);
    this.response = Objects.requireNonNull(response);
  }

  public ReturnableException(String message, Response<?> response) {
    this(message, null, response);
  }

  public ReturnableException(Throwable cause, Response<?> response) {
    this(null, cause, response);
  }

  public ReturnableException(Response<?> response) {
    this(null, null, response);
  }

  public ReturnableException(HttpStatus status, String requestId) {
    response = ResponseUtils.status(status, requestId);
  }

  public ReturnableException(HttpStatus status, String requestId, ListValueMap<String, String> errors) {
    response = ResponseUtils.status(status, requestId);
    response.setErrors(errors);
  }

  public HttpStatus getStatus() {
    return Optional.ofNullable(response.getCode())
        .map(HttpStatus::resolve)
        .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
