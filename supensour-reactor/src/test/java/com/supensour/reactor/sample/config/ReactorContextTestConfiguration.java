package com.supensour.reactor.sample.config;

import com.supensour.reactor.context.EnableReactorContextInjection;
import com.supensour.reactor.context.ReactorContextConfiguration;
import com.supensour.reactor.context.ReactorContextSetting;
import com.supensour.reactor.context.ReactorContextSettingRegistry;
import org.slf4j.MDC;

import java.util.Map;
import java.util.Optional;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@EnableReactorContextInjection
public class ReactorContextTestConfiguration implements ReactorContextConfiguration {

  public static final String MDC_CONTEXT_KEY = "MDC";

  @Override
  public void registerContextSetting(ReactorContextSettingRegistry registry) {
    registry.register(ReactorContextSetting.<Map<String, String>>builder()
        .key(MDC_CONTEXT_KEY)
        .getter((context, key) -> MDC.getCopyOfContextMap())
        .setter((data, context, key) -> Optional.ofNullable(data).ifPresent(MDC::setContextMap))
        .build());
  }

}
