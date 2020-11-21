package com.supensour.webflux.handler;

import com.supensour.core.model.exception.ErrorsException;
import com.supensour.core.model.exception.ReturnableException;
import com.supensour.core.utils.ErrorUtils;
import com.supensour.core.utils.ResponseUtils;
import com.supensour.model.constant.FieldNames;
import com.supensour.model.map.ListValueMap;
import com.supensour.model.web.Response;
import com.supensour.webflux.model.function.ErrorHandlerPredicate;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;

import java.util.Optional;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public abstract class AbstractWebExceptionHandler extends AbstractEmptyWebExceptionHandler {

  protected AbstractWebExceptionHandler(ErrorAttributes errorAttributes,
                                        ResourceProperties resourceProperties,
                                        ErrorProperties errorProperties,
                                        ApplicationContext applicationContext) {
    super(errorAttributes, resourceProperties, errorProperties, applicationContext);
  }

  @Override
  protected void registerHandlers() {
    registerHandler(isInstanceOf(WebExchangeBindException.class), this::webExchangeBindException);
    registerHandler(isInstanceOf(ServerWebInputException.class), this::serverWebInputException);
    registerHandler(isInstanceOf(ResponseStatusException.class), this::responseStatusException);
    registerHandler(isInstanceOf(ReturnableException.class), this::returnableException);
    registerHandler(isInstanceOf(ErrorsException.class), this::errorsException);
    registerHandler(isInstanceOf(Throwable.class), this::throwable, Ordered.LOWEST_PRECEDENCE);
  }

  protected Response<Object> webExchangeBindException(Throwable throwable, ServerRequest request) {
    WebExchangeBindException e = (WebExchangeBindException) throwable;
    ListValueMap<String, String> errors = ErrorUtils.mapBindingResult(e.getBindingResult());
    log(HttpStatus.BAD_REQUEST, e, request, errors);
    return ResponseUtils.badRequest(getRequestId(e, request), errors);
  }

  protected Response<Object> serverWebInputException(Throwable throwable, ServerRequest request) {
    ServerWebInputException e = (ServerWebInputException) throwable;
    ListValueMap<String, String> errors = ErrorUtils.mapServerWebInputException(e);
    log(HttpStatus.BAD_REQUEST, e, request, errors);
    return ResponseUtils.badRequest(getRequestId(e, request), errors);
  }

  protected Response<Object> responseStatusException(Throwable throwable, ServerRequest request) {
    ResponseStatusException e = (ResponseStatusException) throwable;
    Response<Object> response = ResponseUtils.status(e.getStatus(), getRequestId(e, request));
    Optional.ofNullable(e.getReason())
        .filter(StringUtils::hasText)
        .ifPresent(reason -> response.getErrors().add(FieldNames.DEFAULT, reason));
    log(e.getStatus(), e, request, null);
    return response;
  }

  protected Response<Object> returnableException(Throwable throwable, ServerRequest request) {
    ReturnableException e = (ReturnableException) throwable;
    @SuppressWarnings("unchecked")
    Response<Object> response = (Response<Object>) e.getResponse();
    log(e.getStatus(), e, request, response.getErrors());
    return response;
  }

  protected Response<Object> errorsException(Throwable throwable, ServerRequest request) {
    ErrorsException e = (ErrorsException) throwable;
    Response<Object> response = ResponseUtils.status(e.getStatus(), getRequestId(e, request));
    response.setNativeErrorMap(e.getErrors());
    log(e.getStatus(), e, request, e.getErrors());
    return response;
  }

  protected Response<Object> throwable(Throwable throwable, ServerRequest request) {
    HttpStatus status = resolveGeneralThrowableStatus(throwable);
    Response<Object> response = ResponseUtils.status(status, getRequestId(throwable, request));
    Optional.ofNullable(throwable.getMessage())
        .filter(StringUtils::hasText)
        .ifPresent(message -> response.getErrors().add(FieldNames.DEFAULT, message));
    log(status, throwable, request, null);
    return response;
  }

  @SuppressWarnings("DuplicatedCode")
  protected HttpStatus resolveGeneralThrowableStatus(Throwable throwable) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ResponseStatus responseStatus = throwable.getClass().getAnnotation(ResponseStatus.class);
    if (responseStatus != null) {
      if (responseStatus.code() == responseStatus.value() || responseStatus.code() != HttpStatus.INTERNAL_SERVER_ERROR) {
        status = responseStatus.code();
      } else {
        status = responseStatus.value();
      }
    }
    return status;
  }

  protected ErrorHandlerPredicate isInstanceOf(Class<?> clazz) {
    return (throwable, request) -> clazz.isInstance(throwable);
  }

}
