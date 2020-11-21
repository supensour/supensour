package com.supensour.webflux.sample.handler;

import com.supensour.model.web.Response;
import com.supensour.webflux.handler.AbstractWebExceptionHandler;
import com.supensour.webflux.sample.exception.IgnoredException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.server.ServerRequest;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Slf4j
public class WebExceptionHandler extends AbstractWebExceptionHandler {

  public static final String REQUEST_ID = "test-request-id";

  public WebExceptionHandler(ErrorAttributes errorAttributes,
                             ResourceProperties resourceProperties,
                             ErrorProperties errorProperties,
                             ApplicationContext applicationContext) {
    super(errorAttributes, resourceProperties, errorProperties, applicationContext);
  }

  @Override
  protected void registerHandlers() {
    super.registerHandlers();
    registerHandler(isInstanceOf(IgnoredException.class), this::ignoredException);
  }

  private Response<Object> ignoredException(Throwable e, ServerRequest request) {
    return null;
  }

  @Override
  protected Logger getLogger() {
    return log;
  }

  @Override
  protected String getRequestId(Throwable e, ServerRequest request) {
    return REQUEST_ID;
  }

}
