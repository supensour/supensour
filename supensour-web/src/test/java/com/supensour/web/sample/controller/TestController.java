package com.supensour.web.sample.controller;

import com.supensour.core.model.exception.ErrorsException;
import com.supensour.core.model.exception.ReturnableException;
import com.supensour.model.map.impl.DefaultListValueMap;
import com.supensour.web.sample.dto.User;
import com.supensour.web.sample.handler.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@RestController
public class TestController {

  @PostMapping(path = "/methodArgumentNotValidException")
  public User methodArgumentNotValidException(@Valid @RequestBody User user) {
    return user;
  }

  @PostMapping(path = "/missingServletRequestParameterException")
  public Integer missingServletRequestParameterException(@RequestParam Integer number) {
    return number;
  }

  @PostMapping(path = "/methodArgumentTypeMismatchException")
  public Integer methodArgumentTypeMismatchException(@RequestParam Integer number) {
    return number;
  }

  @GetMapping(path = "/bindException")
  public User bindException(User user) {
    return user;
  }

  @GetMapping(path = "/httpRequestMethodNotSupportedException")
  public Integer httpRequestMethodNotSupportedException() {
    return 0;
  }

  @PostMapping(path = "/httpMessageNotReadableException")
  public User httpMessageNotReadableException(@RequestBody User user) {
    return user;
  }

  @PostMapping(path = "/httpMediaTypeNotSupportedException",
              consumes = MediaType.APPLICATION_JSON_VALUE)
  public Integer httpMediaTypeNotSupportedException() {
    return 0;
  }

  @PostMapping(path = "/httpMediaTypeNotAcceptableException",
               produces = MediaType.TEXT_HTML_VALUE)
  public Integer httpMediaTypeNotAcceptableException() {
    return 0;
  }

  @GetMapping(path = "/responseStatusException")
  public Integer responseStatusException() {
    throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
  }

  @GetMapping(path = "/returnableException")
  public Object returnableException() {
    throw new ReturnableException(HttpStatus.NOT_FOUND, ErrorController.REQUEST_ID);
  }

  @GetMapping(path = "/errorsException")
  public Object errorsException() {
    throw new ErrorsException(HttpStatus.BAD_REQUEST, new DefaultListValueMap<>());
  }

  // --

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
