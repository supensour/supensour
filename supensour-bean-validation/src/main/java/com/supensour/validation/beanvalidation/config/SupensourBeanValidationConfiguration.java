package com.supensour.validation.beanvalidation.config;

import com.supensour.validation.beanvalidation.Validator;
import com.supensour.validation.beanvalidation.handler.ValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Configuration
public class SupensourBeanValidationConfiguration {

  @Bean
  public Validator validator(javax.validation.Validator validator) {
    return new ValidatorImpl(validator);
  }

}
