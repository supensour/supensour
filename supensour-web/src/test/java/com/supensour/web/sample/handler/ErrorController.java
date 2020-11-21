package com.supensour.web.sample.handler;

import com.supensour.web.handler.BaseErrorControllerHandler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Slf4j
@RestControllerAdvice
public class ErrorController implements BaseErrorControllerHandler {

  public static final String REQUEST_ID = "test-request-id";

  @Override
  public Logger getLogger() {
    return log;
  }

  @Override
  public String getRequestId(Throwable e, HttpServletRequest request, HttpServletResponse response) {
    return REQUEST_ID;
  }

}
