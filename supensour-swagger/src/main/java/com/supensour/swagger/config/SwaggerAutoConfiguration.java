package com.supensour.swagger.config;

import com.supensour.core.properties.AppInfoProperties;
import com.supensour.model.common.DataRegistry;
import io.swagger.annotations.Api;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Configuration
@ConditionalOnClass(EnableSwagger2.class)
public class SwaggerAutoConfiguration implements ApplicationContextAware {

  @Autowired
  private AppInfoProperties appInfoProperties;

  @Setter
  private ApplicationContext applicationContext;

  @Bean
  @ConditionalOnMissingBean
  public Docket swaggerDocket() {
    List<SwaggerConfiguration> configs = getSwaggerConfigurations();
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
        .build()
        .apiInfo(buildApiInfo())
        .useDefaultResponseMessages(false)
        .genericModelSubstitutes(getGenericModelSubstitutes(configs))
        .ignoredParameterTypes(getIgnoredParameterTypes(configs))
        .globalRequestParameters(getGlobalParameters(configs));
  }

  private List<SwaggerConfiguration> getSwaggerConfigurations() {
    return new ArrayList<>(applicationContext.getBeansOfType(SwaggerConfiguration.class).values());
  }

  private ApiInfo buildApiInfo() {
    return new ApiInfoBuilder()
        .title(getTitle())
        .version(getVersion())
        .description(getDescription())
        .build();
  }

  private String getTitle() {
    String title = Optional.ofNullable(appInfoProperties.getName())
        .filter(StringUtils::hasText)
        .orElseGet(appInfoProperties::getArtifactId);
    return Optional.ofNullable(title)
        .filter(StringUtils::hasText)
        .orElseGet(ApiInfo.DEFAULT::getTitle);
  }

  private String getVersion() {
    return Optional.ofNullable(appInfoProperties.getVersion())
        .filter(StringUtils::hasText)
        .orElseGet(ApiInfo.DEFAULT::getVersion);
  }

  private String getDescription() {
    return Optional.ofNullable(appInfoProperties.getDescription())
        .filter(StringUtils::hasText)
        .orElseGet(ApiInfo.DEFAULT::getDescription);
  }

  private Class<?>[] getGenericModelSubstitutes(List<SwaggerConfiguration> configs) {
    DataRegistry<Class<?>> registry = DataRegistry.create();
    configs.forEach(config -> config.addGenericModelSubstitutes(registry));
    return registry.collect().stream()
        .filter(Objects::nonNull)
        .toArray(Class[]::new);
  }

  private Class<?>[] getIgnoredParameterTypes(List<SwaggerConfiguration> configs) {
    DataRegistry<Class<?>> registry = DataRegistry.create();
    configs.forEach(config -> config.addIgnoredParameterTypes(registry));
    return registry.collect().stream()
        .filter(Objects::nonNull)
        .toArray(Class[]::new);
  }

  private List<RequestParameter> getGlobalParameters(List<SwaggerConfiguration> configs) {
    DataRegistry<RequestParameter> registry = DataRegistry.create();
    configs.forEach(config -> config.addGlobalParameter(registry));
    return registry.collect().stream()
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

}
