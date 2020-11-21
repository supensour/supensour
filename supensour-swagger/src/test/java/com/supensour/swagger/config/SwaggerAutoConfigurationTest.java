package com.supensour.swagger.config;

import com.supensour.swagger.sample.TestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springfox.documentation.spring.web.plugins.Docket;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@SpringBootTest(classes = TestApplication.class)
class SwaggerAutoConfigurationTest {

  @Autowired
  private Docket docket;

  @Test
  void docket() {
    assertNotNull(docket);
  }

}
