package com.supensour.webflux.config;

import com.supensour.webflux.handler.AbstractEmptyWebExceptionHandler;
import lombok.Setter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.stream.Collectors;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public abstract class AbstractWebExceptionHandlerConfiguration implements ApplicationContextAware {

  @Setter
  protected ApplicationContext applicationContext;

  @Autowired
  protected ServerProperties serverProperties;

  @Bean
  @Order(value = -2)
  public AbstractEmptyWebExceptionHandler webExceptionHandler(ErrorAttributes errorAttributes,
                                                              ResourceProperties resourceProperties,
                                                              ObjectProvider<ViewResolver> viewResolvers,
                                                              ServerCodecConfigurer serverCodecConfigurer) {
    AbstractEmptyWebExceptionHandler webExceptionHandler = create(errorAttributes, resourceProperties,
        serverProperties.getError(), applicationContext);
    webExceptionHandler.setViewResolvers(viewResolvers.orderedStream().collect(Collectors.toList()));
    webExceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
    webExceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
    return webExceptionHandler;
  }

  protected abstract AbstractEmptyWebExceptionHandler create(ErrorAttributes errorAttributes,
                                                             ResourceProperties resourceProperties,
                                                             ErrorProperties errorProperties,
                                                             ApplicationContext applicationContext);

}
