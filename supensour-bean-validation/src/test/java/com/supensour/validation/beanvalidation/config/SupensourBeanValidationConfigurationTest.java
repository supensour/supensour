package com.supensour.validation.beanvalidation.config;

import com.supensour.validation.beanvalidation.Validator;
import com.supensour.validation.beanvalidation.sample.TestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@SpringBootTest(classes = TestApplication.class)
class SupensourBeanValidationConfigurationTest {

  @Autowired
  private Validator validator;

  @Test
  void validator() {
    assertNotNull(validator);
  }

}
