package com.supensour.core.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@ExtendWith(MockitoExtension.class)
class ErrorTolerantUtilsTest {

  private static final String RESULT_STR = "result";
  private static final String ALTERNATIVE_RESULT_STR = "alternativeResult";

  @Mock
  private Runnable runnable;

  @Mock
  private Callable<Object> callable;

  @Mock
  private Consumer<Exception> errorConsumer;

  @Mock
  private Function<Exception, Object> errorMapper;

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(runnable);
    verifyNoMoreInteractions(callable);
    verifyNoMoreInteractions(errorConsumer);
    verifyNoMoreInteractions(errorMapper);
  }

  @Test
  void executeRunnable_valid_success() {
    ErrorTolerantUtils.execute(runnable);
    verify(runnable, times(1)).run();
  }

  @Test
  void executeRunnable_invalid_success() {
    doThrow(IllegalStateException.class).when(runnable).run();

    ErrorTolerantUtils.execute(runnable);
    verify(runnable, times(1)).run();
  }

  @Test
  void executeRunnableWithOnerror_valid_success() {
    ErrorTolerantUtils.execute(runnable, errorConsumer);
    verify(runnable, times(1)).run();
    verify(errorConsumer, times(0)).accept(any(Exception.class));
  }

  @Test
  void executeRunnableWithOnerror_invalid_success() {
    doThrow(IllegalStateException.class).when(runnable).run();

    ErrorTolerantUtils.execute(runnable, errorConsumer);
    verify(runnable, times(1)).run();
    verify(errorConsumer, times(1)).accept(any(IllegalStateException.class));
  }

  @Test
  void executeCallable_valid_success() throws Exception {
    when(callable.call()).thenReturn(RESULT_STR);

    assertEquals(RESULT_STR, ErrorTolerantUtils.execute(callable));
    verify(callable, times(1)).call();
  }

  @Test
  void executeCallable_invalid_success() throws Exception {
    when(callable.call()).thenThrow(IllegalStateException.class);

    assertNull(ErrorTolerantUtils.execute(callable));
    verify(callable, times(1)).call();
  }

  @Test
  void executeCallableWithConsumer_valid_success() throws Exception {
    when(callable.call()).thenReturn(RESULT_STR);

    assertEquals(RESULT_STR, ErrorTolerantUtils.execute(callable, errorConsumer));
    verify(callable, times(1)).call();
    verify(errorConsumer, times(0)).accept(any(Exception.class));
  }

  @Test
  void executeCallableWithConsumer_invalid_success() throws Exception {
    when(callable.call()).thenThrow(IllegalStateException.class);

    assertNull(ErrorTolerantUtils.execute(callable, errorConsumer));
    verify(callable, times(1)).call();
    verify(errorConsumer, times(1)).accept(any(IllegalStateException.class));
  }

  @Test
  void executeCallableWithOnErrorResultProvider_valid_success() throws Exception {
    when(callable.call()).thenReturn(RESULT_STR);

    assertEquals(RESULT_STR, ErrorTolerantUtils.executeOr(callable, errorMapper));
    verify(callable, times(1)).call();
    verify(errorMapper, times(0)).apply(any(Exception.class));
  }

  @Test
  void executeCallableWithOnErrorResultProvider_invalid_success() throws Exception {
    when(callable.call()).thenThrow(IllegalStateException.class);
    when(errorMapper.apply(any(IllegalStateException.class))).thenReturn(ALTERNATIVE_RESULT_STR);

    assertEquals(ALTERNATIVE_RESULT_STR, ErrorTolerantUtils.executeOr(callable, errorMapper));
    verify(callable, times(1)).call();
    verify(errorMapper, times(1)).apply(any(IllegalStateException.class));
  }

}
