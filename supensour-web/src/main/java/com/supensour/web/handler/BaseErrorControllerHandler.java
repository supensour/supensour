package com.supensour.web.handler;

import com.supensour.core.model.exception.ErrorsException;
import com.supensour.core.model.exception.ReturnableException;
import com.supensour.core.utils.ErrorUtils;
import com.supensour.core.utils.ResponseUtils;
import com.supensour.model.constant.ErrorCodes;
import com.supensour.model.constant.FieldNames;
import com.supensour.model.map.ListValueMap;
import com.supensour.model.map.impl.DefaultListValueMap;
import com.supensour.model.web.Response;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

/**
 * For webflux, please use AbstractWebExceptionHandler from supensour-webflux module.
 *
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public interface BaseErrorControllerHandler {

  Logger getLogger();

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  default Response<Object> methodArgumentNotValidException(MethodArgumentNotValidException e,
                                                           HttpServletRequest servletRequest,
                                                           HttpServletResponse servletResponse) {
    String requestId = getRequestId(e, servletRequest, servletResponse);
    ListValueMap<String, String> errors = ErrorUtils.mapBindingResult(e.getBindingResult());
    log(HttpStatus.BAD_REQUEST, e, servletRequest, servletResponse, errors);
    return ResponseUtils.badRequest(requestId, errors);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MissingServletRequestParameterException.class)
  default Response<Object> missingServletRequestParameterException(MissingServletRequestParameterException e,
                                                                   HttpServletRequest servletRequest,
                                                                   HttpServletResponse servletResponse) {
    String requestId = getRequestId(e, servletRequest, servletResponse);
    ListValueMap<String, String> errors = new DefaultListValueMap<>(e.getParameterName(), ErrorCodes.NULL);
    log(HttpStatus.BAD_REQUEST, e, servletRequest, servletResponse, errors);
    return ResponseUtils.badRequest(requestId, errors);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  default Response<Object> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e,
                                                               HttpServletRequest servletRequest,
                                                               HttpServletResponse servletResponse) {
    String requestId = getRequestId(e, servletRequest, servletResponse);
    ListValueMap<String, String> errors = new DefaultListValueMap<>();
    errors.addAll(e.getName(), Arrays.asList(ErrorCodes.INVALID_TYPE, e.getMessage()));
    log(HttpStatus.BAD_REQUEST, e, servletRequest, servletResponse, errors);
    return ResponseUtils.badRequest(requestId, errors);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BindException.class)
  default Response<Object> bindException(BindException e,
                                         HttpServletRequest servletRequest,
                                         HttpServletResponse servletResponse) {
    String requestId = getRequestId(e, servletRequest, servletResponse);
    ListValueMap<String, String> errors = ErrorUtils.mapBindingResult(e.getBindingResult());
    log(HttpStatus.BAD_REQUEST, e, servletRequest, servletResponse, errors);
    return ResponseUtils.badRequest(requestId, errors);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoHandlerFoundException.class)
  default Response<Object> noHandlerFoundException(NoHandlerFoundException e,
                                                   HttpServletRequest servletRequest,
                                                   HttpServletResponse servletResponse) {
    String requestId = getRequestId(e, servletRequest, servletResponse);
    log(HttpStatus.NOT_FOUND, e, servletRequest, servletResponse, null);
    return ResponseUtils.status(HttpStatus.NOT_FOUND, requestId);
  }

  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  default Response<Object> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e,
                                                                  HttpServletRequest servletRequest,
                                                                  HttpServletResponse servletResponse) {
    String requestId = getRequestId(e, servletRequest, servletResponse);
    log(HttpStatus.METHOD_NOT_ALLOWED, e, servletRequest, servletResponse, null);
    return ResponseUtils.status(HttpStatus.METHOD_NOT_ALLOWED, requestId);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  default Response<Object> httpMessageNotReadableException(HttpMessageNotReadableException e,
                                                           HttpServletRequest servletRequest,
                                                           HttpServletResponse servletResponse) {
    String requestId = getRequestId(e, servletRequest, servletResponse);
    ListValueMap<String, String> errors = ErrorUtils.mapHttpMessageNotReadableException(e);
    Response<Object> response = ResponseUtils.status(HttpStatus.BAD_REQUEST, requestId, errors);
    log(HttpStatus.BAD_REQUEST, e, servletRequest, servletResponse, null);
    return response;
  }

  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  default Response<Object> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e,
                                                              HttpServletRequest servletRequest,
                                                              HttpServletResponse servletResponse) {
    String requestId = getRequestId(e, servletRequest, servletResponse);
    log(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e, servletRequest, servletResponse, null);
    return ResponseUtils.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE, requestId);
  }

  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  default Response<Object> httpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e,
                                                               HttpServletRequest servletRequest,
                                                               HttpServletResponse servletResponse) {
    String requestId = getRequestId(e, servletRequest, servletResponse);
    log(HttpStatus.NOT_ACCEPTABLE, e, servletRequest, servletResponse, null);
    return ResponseUtils.status(HttpStatus.NOT_ACCEPTABLE, requestId);
  }

  @ExceptionHandler(ResponseStatusException.class)
  default Response<Object> responseStatusException(ResponseStatusException e,
                                                   HttpServletRequest servletRequest,
                                                   HttpServletResponse servletResponse) {
    servletResponse.setStatus(e.getStatus().value());
    String requestId = getRequestId(e, servletRequest, servletResponse);
    log(e.getStatus(), e, servletRequest, servletResponse, null);
    return ResponseUtils.status(e.getStatus(), requestId);
  }

  @ExceptionHandler(ReturnableException.class)
  default Response<?> returnableException(ReturnableException e,
                                          HttpServletRequest servletRequest,
                                          HttpServletResponse servletResponse) {
    servletResponse.setStatus(e.getStatus().value());
    log(e.getStatus(), e, servletRequest, servletResponse, e.getResponse().getErrors());
    return e.getResponse();
  }

  @ExceptionHandler(ErrorsException.class)
  default Response<Object> errorsException(ErrorsException e,
                                           HttpServletRequest servletRequest,
                                           HttpServletResponse servletResponse) {
    servletResponse.setStatus(e.getStatus().value());
    String requestId = getRequestId(e, servletRequest, servletResponse);
    log(e.getStatus(), e, servletRequest, servletResponse, e.getErrors());
    return ResponseUtils.status(e.getStatus(), requestId, e.getErrors());
  }

  @ExceptionHandler(Throwable.class)
  default Response<Object> throwable(Throwable e,
                                     HttpServletRequest servletRequest,
                                     HttpServletResponse servletResponse) {
    HttpStatus status = resolveGeneralThrowableStatus(e);
    servletResponse.setStatus(status.value());
    String requestId = getRequestId(e, servletRequest, servletResponse);
    Response<Object> response = ResponseUtils.status(status, requestId);
    Optional.ofNullable(e.getMessage())
        .filter(StringUtils::hasText)
        .ifPresent(message -> response.getErrors().add(FieldNames.DEFAULT, message));
    log(status, e, servletRequest, servletResponse, null);
    return response;
  }

  @SuppressWarnings("DuplicatedCode")
  private HttpStatus resolveGeneralThrowableStatus(Throwable throwable) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    var responseStatus = throwable.getClass().getAnnotation(ResponseStatus.class);
    if (responseStatus != null) {
      if (responseStatus.code() == responseStatus.value() || responseStatus.code() != HttpStatus.INTERNAL_SERVER_ERROR) {
        status = responseStatus.code();
      } else {
        status = responseStatus.value();
      }
    }
    return status;
  }

  default void log(HttpStatus status, Throwable e, HttpServletRequest request, HttpServletResponse response, Object errors) {
    if (status.is5xxServerError()) {
      getLogger().error(getMessage(e, request, response, errors), e);
    } else {
      getLogger().warn(getMessage(e, request, response, errors), e);
    }
  }

  @SuppressWarnings("DuplicatedCode")
  default String getMessage(Throwable e, HttpServletRequest request, HttpServletResponse response, Object errors) {
    var messageBuilder = new StringBuilder(e.getClass().getName());

    Optional.ofNullable(e.getMessage())
        .filter(StringUtils::hasText)
        .ifPresent(message -> messageBuilder.append(": ").append(message));

    Optional.ofNullable(errors)
        .ifPresent(errs -> messageBuilder.append(" [").append(errs).append("]"));

    return getLogPrefix(e, request, response) + messageBuilder.toString();
  }

  default String getLogPrefix(Throwable e, HttpServletRequest request, HttpServletResponse response) {
    var prefix = new StringBuilder();

    Optional.ofNullable(getRequestId(e, request, response))
        .filter(StringUtils::hasText)
        .ifPresent(requestId -> prefix.append("[").append(requestId).append("] "));

    return prefix.toString();
  }

  String getRequestId(Throwable e, HttpServletRequest request, HttpServletResponse response);

}
