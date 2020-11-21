package com.supensour.webflux.model.function;

import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.function.BiPredicate;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@FunctionalInterface
public interface ErrorHandlerPredicate extends BiPredicate<Throwable, ServerRequest> {

}
