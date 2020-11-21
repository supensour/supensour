package com.supensour.swagger.config.defaults;

import com.supensour.model.common.DataRegistry;
import com.supensour.swagger.config.SwaggerConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import rx.Observable;
import rx.Single;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Configuration
@ConditionalOnClass(value = {EnableSwagger2.class, Single.class, Observable.class})
public class SwaggerRxJavaConfiguration implements SwaggerConfiguration {

  @Override
  public void addGenericModelSubstitutes(DataRegistry<Class<?>> registry) {
    registry.register(Single.class);
    registry.register(Observable.class);
  }

}
