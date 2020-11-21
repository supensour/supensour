package com.supensour.webflux.model.function;

import com.supensour.model.web.Response;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.function.BiFunction;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@FunctionalInterface
public interface ErrorHandler extends BiFunction<Throwable, ServerRequest, Response<Object>> {

}
