package com.supensour.reactor.context.handler;

import com.supensour.model.annotation.Experimental;
import com.supensour.reactor.context.ReactorContextConfiguration;
import com.supensour.reactor.context.ReactorContextHelper;
import com.supensour.reactor.context.ReactorContextSetting;
import com.supensour.reactor.context.ReactorContextSettingRegistry;
import lombok.Setter;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;

import java.util.List;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Experimental
@Configuration
@ConditionalOnClass({ Mono.class, Flux.class, Publisher.class})
public class ReactorContextAutoConfiguration implements ApplicationContextAware, InitializingBean, DisposableBean {

  @Setter
  private ApplicationContext applicationContext;

  @Override
  public void afterPropertiesSet() {
    Hooks.onEachOperator(ReactorContextSubscriber.LIFTER_KEY, Operators.lift(this::lifter));
  }

  @Bean
  public ReactorContextHelper reactorContextHelper() {
    return new ReactorContextHelperImpl(getReactorContextSettings());
  }

  private List<ReactorContextSetting<Object>> getReactorContextSettings() {
    var registry = ReactorContextSettingRegistry.create();
    applicationContext.getBeansOfType(ReactorContextConfiguration.class)
        .forEach((key, config) -> config.registerContextSetting(registry));
    return registry.collect();
  }

  private <T>CoreSubscriber<T> lifter(Scannable scannable, CoreSubscriber<T> subscriber) {
    return new ReactorContextSubscriber<>(subscriber, reactorContextHelper());
  }

  @Override
  public void destroy() {
    Hooks.resetOnEachOperator(ReactorContextSubscriber.LIFTER_KEY);
  }

}
