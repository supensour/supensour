package com.supensour.core.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Configuration
@ConfigurationPropertiesScan("com.supensour.core.properties")
public class SupensourCoreAutoConfiguration {

}
