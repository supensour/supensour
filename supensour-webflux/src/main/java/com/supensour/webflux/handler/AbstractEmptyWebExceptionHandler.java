package com.supensour.webflux.handler;

import com.supensour.model.web.Response;
import com.supensour.webflux.model.dto.ErrorHandlerRegistry;
import com.supensour.webflux.model.function.ErrorHandler;
import com.supensour.webflux.model.function.ErrorHandlerPredicate;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.OrderComparator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public abstract class AbstractEmptyWebExceptionHandler extends DefaultErrorWebExceptionHandler {

  private final List<ErrorHandlerRegistry> errorHandlerRegistries = new ArrayList<>();

  protected AbstractEmptyWebExceptionHandler(ErrorAttributes errorAttributes,
                                             ResourceProperties resourceProperties,
                                             ErrorProperties errorProperties,
                                             ApplicationContext applicationContext) {
    super(errorAttributes, resourceProperties, errorProperties, applicationContext);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    super.afterPropertiesSet();
    registerHandlers();
    OrderComparator.sort(errorHandlerRegistries);
  }

  protected abstract Logger getLogger();

  protected abstract void registerHandlers();

  protected void registerHandler(ErrorHandlerPredicate predicate, ErrorHandler handler) {
    registerHandler(predicate, handler, 0);
  }

  protected void registerHandler(ErrorHandlerPredicate predicate, ErrorHandler handler, int order) {
    errorHandlerRegistries.add(ErrorHandlerRegistry.of(predicate, handler, order));
  }

  @Override
  protected void logError(ServerRequest request, ServerResponse response, Throwable throwable) {

  }

  @Override
  protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
    return route(RequestPredicates.all(), this::renderErrorResponse);
  }

  @Override
  protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
    var throwable = getError(request);
    Response<Object> response = handleException(throwable, request);
    if (response == null) {
      return Mono.error(throwable);
    }

    HttpStatus status = Optional.ofNullable(response.getCode())
        .map(HttpStatus::resolve)
        .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    return ServerResponse.status(status)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(response));
  }

  private Response<Object> handleException(Throwable e, ServerRequest request) {
    return errorHandlerRegistries.stream()
        .filter(registry -> registry.shouldHandle(e, request))
        .findFirst()
        .map(registry -> registry.handle(e, request))
        .orElse(null);
  }

  protected void log(HttpStatus status, Throwable e, ServerRequest request, Object errors) {
    if (status.is5xxServerError()) {
      getLogger().error(getMessage(e, request, errors), e);
    } else {
      getLogger().warn(getMessage(e, request, errors), e);
    }
  }

  @SuppressWarnings("DuplicatedCode")
  protected String getMessage(Throwable e, ServerRequest request, Object errors) {
    var messageBuilder = new StringBuilder(e.getClass().getName());

    Optional.ofNullable(e.getMessage())
        .filter(StringUtils::hasText)
        .ifPresent(message -> messageBuilder.append(": ").append(message));

    Optional.ofNullable(errors)
        .ifPresent(errs -> messageBuilder.append(" [").append(errs).append("]"));

    return getLogPrefix(e, request) + messageBuilder.toString();
  }

  protected String getLogPrefix(Throwable e, ServerRequest request) {
    var prefix = new StringBuilder();

    Optional.ofNullable(getRequestId(e, request))
        .filter(StringUtils::hasText)
        .ifPresent(requestId -> prefix.append("[").append(requestId).append("] "));

    return prefix.toString();
  }

  protected abstract String getRequestId(Throwable e, ServerRequest request);

}
