package com.supensour.webflux.sample.controller;

import com.supensour.core.model.exception.ErrorsException;
import com.supensour.core.model.exception.ReturnableException;
import com.supensour.webflux.sample.dto.User;
import com.supensour.webflux.sample.exception.IgnoredException;
import com.supensour.webflux.sample.handler.WebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@RestController
public class TestController {

  @PostMapping(path = "/webExchangeBindException")
  public Mono<User> webExchangeBindException(@Valid @RequestBody User user) {
    return Mono.just(user);
  }

  @SuppressWarnings("unused")
  @PostMapping(path = "/serverWebInputException")
  public Mono<User> serverWebInputException(@RequestBody User user,
                                            @RequestParam Integer number,
                                            @RequestParam(required = false) Date date,
                                            @RequestParam(required = false) User user2) {
    return Mono.just(user);
  }

  @GetMapping(path = "/returnableException")
  public Object returnableException() {
    throw new ReturnableException(HttpStatus.NOT_FOUND, WebExceptionHandler.REQUEST_ID);
  }

  @GetMapping(path = "/errorsException")
  public Object errorsException() {
    throw new ErrorsException(HttpStatus.BAD_REQUEST, null);
  }

  @GetMapping(path = "/throwable")
  public Object throwable() throws Exception {
    throw new Exception("Throwable message");
  }

  @GetMapping(path = "/throwable/response-status/default")
  public Object throwable_responseStatus_default() throws Exception {
    throw new DefaultResponseStatusAnnotatedException();
  }

  @GetMapping(path = "/throwable/response-status/code")
  public Object throwable_responseStatus_code() throws Exception {
    throw new CodeResponseStatusAnnotatedException();
  }

  @GetMapping(path = "/throwable/response-status/status")
  public Object throwable_responseStatus_status() throws Exception {
    throw new ValueResponseStatusAnnotatedException();
  }

  @GetMapping(path = "/ignoredException")
  public Object ignoredException() {
    throw new IgnoredException();
  }

  @SuppressWarnings("unused")
  public static void method(Object param) {}

  @SuppressWarnings("serial")
  @ResponseStatus
  private static class DefaultResponseStatusAnnotatedException extends Exception {

  }

  @SuppressWarnings("serial")
  @ResponseStatus(code = HttpStatus.REQUEST_TIMEOUT)
  private static class CodeResponseStatusAnnotatedException extends Exception {

  }

  @SuppressWarnings("serial")
  @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
  private static class ValueResponseStatusAnnotatedException extends Exception {

  }

}
