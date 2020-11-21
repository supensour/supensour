package com.supensour.core.thread.executor;

import com.supensour.core.thread.function.ThreadContextDestroyer;
import com.supensour.core.thread.function.ThreadContextGetter;
import com.supensour.core.thread.function.ThreadContextSetter;
import com.supensour.core.thread.setting.ContextAwareThreadSetting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@ExtendWith(MockitoExtension.class)
class ContextAwareThreadPoolTaskExecutorTest {

  private static final Object MDC_SETTING_KEY = MDC.class.getName();

  private static final String RESULT = "result";
  private static final String SESSION_ID = "sessionId";
  private static final String SESSION_ID_KEY = "sessionIdKey";

  private static final IllegalStateException EXCEPTION = new IllegalStateException();

  private ContextAwareThreadPoolTaskExecutor executor;

  private ContextAwareThreadSetting<Map<String, String>> mdcSetting;

  @BeforeEach
  void setUp() {
    mdcSetting = buildMdcSetting();

    executor = new ContextAwareThreadPoolTaskExecutor().addSetting(mdcSetting);
    executor.afterPropertiesSet();

    MDC.setContextMap(buildMdcContextMap());
  }

  @AfterEach
  void tearDown() {
    MDC.clear();
    verifyNoMoreInteractions(mdcSetting.getGetter());
    verifyNoMoreInteractions(mdcSetting.getSetter());
    verifyNoMoreInteractions(mdcSetting.getDestroyer());
  }

  @Test
  void submit_runnable() throws Exception {
    Future<?> futureResult = executor.submit((Runnable) this::getResult);
    assertNull(futureResult.get());

    verify(mdcSetting.getGetter(), times(1)).get(anyMap(), eq(MDC_SETTING_KEY));
    verify(mdcSetting.getSetter(), times(1)).set(anyMap(), anyMap(), eq(MDC_SETTING_KEY));
    verify(mdcSetting.getDestroyer(), times(1)).destroy(anyMap(), anyMap(), eq(MDC_SETTING_KEY));
  }

  @Test
  void submit_runnable_throwsException() {
    ExecutionException e = assertThrows(ExecutionException.class,
        () -> executor.submit(this::throwError).get());

    assertTrue(e.getCause() instanceof IllegalStateException);
    assertEquals(EXCEPTION, e.getCause().getCause());
    verify(mdcSetting.getGetter(), times(1)).get(anyMap(), eq(MDC_SETTING_KEY));
    verify(mdcSetting.getSetter(), times(1)).set(anyMap(), anyMap(), eq(MDC_SETTING_KEY));
    verify(mdcSetting.getDestroyer(), times(1)).destroy(anyMap(), anyMap(), eq(MDC_SETTING_KEY));
  }

  @Test
  void submit_callable() throws Exception {
    Future<String> futureResult = executor.submit(this::getResult);
    assertEquals(RESULT, futureResult.get());

    verify(mdcSetting.getGetter(), times(1)).get(anyMap(), eq(MDC_SETTING_KEY));
    verify(mdcSetting.getSetter(), times(1)).set(anyMap(), anyMap(), eq(MDC_SETTING_KEY));
    verify(mdcSetting.getDestroyer(), times(1)).destroy(anyMap(), anyMap(), eq(MDC_SETTING_KEY));
  }

  @Test
  void submitListenable_runnable() throws Exception {
    ListenableFuture<?> listenableFutureResult = executor.submitListenable((Runnable) this::getResult);
    assertNull(listenableFutureResult.get());

    verify(mdcSetting.getGetter(), times(1)).get(anyMap(), eq(MDC_SETTING_KEY));
    verify(mdcSetting.getSetter(), times(1)).set(anyMap(), anyMap(), eq(MDC_SETTING_KEY));
    verify(mdcSetting.getDestroyer(), times(1)).destroy(anyMap(), anyMap(), eq(MDC_SETTING_KEY));
  }

  @Test
  void submitListenable_runnable_throwsException() {
    ExecutionException e = assertThrows(ExecutionException.class,
        () -> executor.submitListenable(this::throwError).get());

    assertTrue(e.getCause() instanceof IllegalStateException);
    assertEquals(EXCEPTION, e.getCause().getCause());
    verify(mdcSetting.getGetter(), times(1)).get(anyMap(), eq(MDC_SETTING_KEY));
    verify(mdcSetting.getSetter(), times(1)).set(anyMap(), anyMap(), eq(MDC_SETTING_KEY));
    verify(mdcSetting.getDestroyer(), times(1)).destroy(anyMap(), anyMap(), eq(MDC_SETTING_KEY));
  }

  @Test
  void submitListenable_callable() throws Exception {
    ListenableFuture<String> listenableFutureResult = executor.submitListenable(this::getResult);
    assertEquals(RESULT, listenableFutureResult.get());

    verify(mdcSetting.getGetter(), times(1)).get(anyMap(), eq(MDC_SETTING_KEY));
    verify(mdcSetting.getSetter(), times(1)).set(anyMap(), anyMap(), eq(MDC_SETTING_KEY));
    verify(mdcSetting.getDestroyer(), times(1)).destroy(anyMap(), anyMap(), eq(MDC_SETTING_KEY));
  }

  private String getResult() {
    assertInitializedMdcContextMap();
    return RESULT;
  }

  private void throwError() {
    assertInitializedMdcContextMap();
    throw EXCEPTION;
  }

  private void assertInitializedMdcContextMap() {
    assertEquals(1, MDC.getCopyOfContextMap().size());
    assertEquals(SESSION_ID, MDC.get(SESSION_ID_KEY));
  }

  private Map<String, String> buildMdcContextMap() {
    Map<String, String> context = new HashMap<>();
    context.put(SESSION_ID_KEY, SESSION_ID);
    return context;
  }

  private ContextAwareThreadSetting<Map<String, String>> buildMdcSetting() {
    return ContextAwareThreadSetting.<Map<String, String>>builder()
        .key(MDC_SETTING_KEY)
        .getter(buildMdcContextGetter())
        .setter(buildMdcContextSetter())
        .destroyer(buildMdcContextDestroyer())
        .build();
  }

  private ThreadContextGetter<Map<String, String>> buildMdcContextGetter() {
    //noinspection Convert2Lambda
    return spy(new ThreadContextGetter<Map<String, String>>() {
      @Override
      public Map<String, String> get(Map<Object, Object> context, Object key) {
        return MDC.getCopyOfContextMap();
      }
    });
  }

  private ThreadContextSetter<Map<String, String>> buildMdcContextSetter() {
    //noinspection Convert2Lambda
    return spy(new ThreadContextSetter<Map<String, String>>() {
      @Override
      public void set(Map<String, String> data, Map<Object, Object> context, Object key) {
        Optional.ofNullable(data).ifPresent(MDC::setContextMap);
      }
    });
  }

  private ThreadContextDestroyer<Map<String, String>> buildMdcContextDestroyer() {
    //noinspection Convert2Lambda
    return spy(new ThreadContextDestroyer<Map<String, String>>() {
      @Override
      public void destroy(Map<String, String> data, Map<Object, Object> context, Object key) {
        MDC.clear();
      }
    });
  }

}
