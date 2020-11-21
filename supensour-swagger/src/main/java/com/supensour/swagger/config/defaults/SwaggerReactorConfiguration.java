package com.supensour.swagger.config.defaults;

import com.supensour.model.common.DataRegistry;
import com.supensour.swagger.config.SwaggerConfiguration;
import org.reactivestreams.Publisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Configuration
@ConditionalOnClass(value = {EnableSwagger2.class, Mono.class, Publisher.class, Flux.class})
public class SwaggerReactorConfiguration implements SwaggerConfiguration {

  @Override
  public void addGenericModelSubstitutes(DataRegistry<Class<?>> registry) {
    registry.register(Mono.class);
    registry.register(Publisher.class);
    registry.register(Flux.class);
  }

}
