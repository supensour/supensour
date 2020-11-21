package com.supensour.swagger.config.defaults;

import com.supensour.model.common.DataRegistry;
import com.supensour.swagger.config.SwaggerConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.server.ServerWebExchange;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Configuration
@ConditionalOnClass(value = {EnableSwagger2.class, ServerWebExchange.class})
public class SwaggerWebConfiguration implements SwaggerConfiguration {

  @Override
  public void addGenericModelSubstitutes(DataRegistry<Class<?>> registry) {
    registry.register(ResponseEntity.class);
    registry.register(DeferredResult.class);
  }

  @Override
  public void addIgnoredParameterTypes(DataRegistry<Class<?>> registry) {
    registry.register(ServerWebExchange.class);
    registry.register(ServerHttpRequest.class);
    registry.register(org.springframework.http.server.reactive.ServerHttpRequest.class);
    registry.register(ServerHttpResponse.class);
    registry.register(org.springframework.http.server.reactive.ServerHttpResponse.class);
  }

}
