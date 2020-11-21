package com.supensour.core.config;

import com.supensour.core.sample.TestApplication;
import com.supensour.core.properties.AppInfoProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@SpringBootTest(classes = TestApplication.class)
class SupensourCoreAutoConfigurationTest {

  @Autowired
  private AppInfoProperties appInfoProperties;

  @Test
  void appInfoProperties() {
    assertNotNull(appInfoProperties);
    assertEquals("supensour-core", appInfoProperties.getArtifactId());
    assertEquals("com.supensour", appInfoProperties.getGroupId());
  }

}
