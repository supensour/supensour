package com.supensour.reactor.context.handler;

import com.supensour.core.utils.CollectionUtils;
import com.supensour.reactor.context.ReactorContextHelper;
import com.supensour.reactor.sample.TestApplication;
import com.supensour.reactor.sample.config.ReactorContextTestConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.context.Context;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Slf4j
@SpringBootTest(classes = {
    TestApplication.class,
    ReactorContextTestConfiguration.class
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReactorContextSubscriberTest {

  private static final String USER_ID_KEY = "User-ID";
  private static final String USER_ID = "user-id";

  @Autowired
  private ReactorContextHelper reactorContextHelper;

  @Autowired
  private ReactorContextAutoConfiguration configuration;

  @BeforeEach
  void setUp() {
    MDC.put(USER_ID_KEY, USER_ID);
  }

  @AfterEach
  void tearDown() {
    MDC.clear();
  }

  @Test
  void success() {
    Mono<Context> mono = Mono.subscriberContext()
        .map(ReactorContextSubscriberTest::peek)
        .zipWith(Mono.subscriberContext()
            .map(ReactorContextSubscriberTest::peek), (context, context2) -> context)
        .map(ReactorContextSubscriberTest::peek)
        .map(ReactorContextSubscriberTest::throwError)
        .doOnError(ReactorContextSubscriberTest::doOnError)
        .onErrorResume(throwable -> Mono.subscriberContext())
        .doOnSuccess(ReactorContextSubscriberTest::doOnSuccess)
        .subscribeOn(Schedulers.elastic())
        .subscriberContext(reactorContextHelper::injectContext);
    StepVerifier.create(mono)
        .consumeNextWith(ReactorContextSubscriberTest::assertInjectedContext)
        .verifyComplete();
  }

  @Test
  @Order(value = Integer.MAX_VALUE)
  void lifterRemoved() {
    configuration.destroy();
    Mono<Context> mono = Mono.subscriberContext()
        .map(context -> {
          log.info("Context {}", context);
          log.info("MDC {}", MDC.getCopyOfContextMap());
          assertInjectedContext(context);
          assertEmptyMdc();
          return context;
        })
        .doOnNext(context -> {
          log.info("Context {}", context);
          log.info("MDC {}", MDC.getCopyOfContextMap());
          assertInjectedContext(context);
          assertEmptyMdc();
        })
        .subscribeOn(Schedulers.boundedElastic())
        .subscriberContext(reactorContextHelper::injectContext);
    StepVerifier.create(mono)
        .consumeNextWith(context -> {
          log.info("Context {}", context);
          log.info("MDC {}", MDC.getCopyOfContextMap());
          assertInjectedContext(context);
          assertEmptyMdc();
        })
        .verifyComplete();
  }

  private static <T> T peek(T data) {
    log.info("Peek: {}", MDC.getCopyOfContextMap());
    assertMdc();
    return data;
  }

  private static <T> T throwError(T data) {
    log.info("Throwing exception: {}", MDC.getCopyOfContextMap());
    assertMdc();
    throw new RuntimeException("Intended exception");
  }

  private static <T> void doOnSuccess(T data) {
    log.info("Success: {}", MDC.getCopyOfContextMap());
    assertMdc();
  }

  private static void doOnError(Throwable throwable) {
    log.error("Failed: {}", MDC.getCopyOfContextMap(), throwable);
    assertMdc();
  }

  private static void assertMdc() {
    assertEquals(1, MDC.getCopyOfContextMap().size());
    assertEquals(USER_ID, MDC.get(USER_ID_KEY));
  }

  private static void assertEmptyMdc() {
    assertTrue(CollectionUtils.isEmpty(MDC.getCopyOfContextMap()));
  }

  private static void assertInjectedContext(Context context) {
    assertEquals(1, context.size());

    Map<Object, Object> injectedContext = context.get(ReactorContextSubscriber.REACTOR_CONTEXT_KEY);
    assertEquals(1, injectedContext.size());

    //noinspection unchecked
    Map<String, String> mdcContext = (Map<String, String>) injectedContext.get(ReactorContextTestConfiguration.MDC_CONTEXT_KEY);
    assertEquals(1, mdcContext.size());
    assertEquals(USER_ID, mdcContext.get(USER_ID_KEY));
  }

}
