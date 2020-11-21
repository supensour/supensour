package com.supensour.webflux.sample.config;

import com.supensour.webflux.config.AbstractWebExceptionHandlerConfiguration;
import com.supensour.webflux.handler.AbstractEmptyWebExceptionHandler;
import com.supensour.webflux.sample.handler.WebExceptionHandler;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Configuration
public class WebExceptionHandlerConfiguration extends AbstractWebExceptionHandlerConfiguration {

  @Override
  protected AbstractEmptyWebExceptionHandler create(ErrorAttributes errorAttributes,
                                                    ResourceProperties resourceProperties,
                                                    ErrorProperties errorProperties,
                                                    ApplicationContext applicationContext) {
    return new WebExceptionHandler(errorAttributes, resourceProperties, errorProperties, applicationContext);
  }

}
