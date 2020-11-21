package com.supensour.swagger.config;

import com.supensour.model.common.DataRegistry;
import springfox.documentation.service.RequestParameter;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public interface SwaggerConfiguration {

  default void addGenericModelSubstitutes(DataRegistry<Class<?>> registry) {

  }

  default void addIgnoredParameterTypes(DataRegistry<Class<?>> registry) {

  }

  default void addGlobalParameter(DataRegistry<RequestParameter> registry) {

  }

}
