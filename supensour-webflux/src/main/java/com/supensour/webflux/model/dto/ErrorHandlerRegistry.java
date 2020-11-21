package com.supensour.webflux.model.dto;

import com.supensour.model.web.Response;
import com.supensour.webflux.model.function.ErrorHandler;
import com.supensour.webflux.model.function.ErrorHandlerPredicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.server.ServerRequest;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@AllArgsConstructor(staticName = "of")
public class ErrorHandlerRegistry implements Ordered {

  @NonNull
  private final ErrorHandlerPredicate predicate;

  @NonNull
  private final ErrorHandler handler;

  @Getter
  private final int order;

  public boolean shouldHandle(Throwable exception, ServerRequest request) {
    return predicate.test(exception, request);
  }

  public Response<Object> handle(Throwable exception, ServerRequest request) {
    return handler.apply(exception, request);
  }

}
