package com.supensour.reactor.context.handler;

import com.supensour.core.thread.function.ThreadContextGetter;
import com.supensour.core.thread.function.ThreadContextSetter;
import com.supensour.reactor.context.ReactorContextSetting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.util.context.Context;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@ExtendWith(MockitoExtension.class)
class ReactorContextHelperImplTest {

  private static final Object KEY = "Key";
  private static final Object DATA = "Data";

  private ReactorContextHelperImpl reactorContextHelper;

  @Mock
  private ReactorContextSetting<Object> setting_1;

  @Mock
  private ReactorContextSetting<Object> setting_2;

  @Test
  void injectContext() {
    List<ReactorContextSetting<Object>> settings = Collections.singletonList(setting_1);
    reactorContextHelper = new ReactorContextHelperImpl(settings);

    mockDefaultKey(setting_1);
    mockDefaultGetter(setting_1);
    Context context = reactorContextHelper.injectContext(null);

    verify(setting_1, times(2)).getKey();
    verify(setting_1, times(1)).getGetter();
    verify(setting_1.getGetter(), times(1)).get(anyMap(), eq(KEY));

    assertEquals(1, context.size());
    Map<Object, Object> injectedContext = context.get(ReactorContextSubscriber.REACTOR_CONTEXT_KEY);
    assertEquals(1, injectedContext.size());
    assertEquals(DATA, injectedContext.get(KEY));
  }

  @Test
  void propagateContext() {
    List<ReactorContextSetting<Object>> settings = Arrays.asList(setting_1, setting_2);
    reactorContextHelper = new ReactorContextHelperImpl(settings);

    Map<Object, Object> injectedContext = new HashMap<>();
    injectedContext.put(KEY, DATA);
    Context context = Context.of(ReactorContextSubscriber.REACTOR_CONTEXT_KEY, injectedContext);

    mockDefaultKey(setting_1);
    mockDefaultSetter(setting_1);
    reactorContextHelper.propagateContext(context);

    verify(setting_1, times(2)).getKey();
    verify(setting_1, times(2)).getSetter();
    verify(setting_1.getSetter(), times(1)).set(DATA, injectedContext, KEY);
    verify(setting_2, never()).getKey();
    verify(setting_2, times(1)).getSetter();
  }

  @Test
  void getContext() {
    reactorContextHelper = new ReactorContextHelperImpl(Collections.emptyList());

    Map<Object, Object> injectedContext = new HashMap<>();
    injectedContext.put(KEY, DATA);
    Context context = Context.of(ReactorContextSubscriber.REACTOR_CONTEXT_KEY, injectedContext);

    assertEquals(DATA, reactorContextHelper.getContext(context, KEY));
  }

  @Test
  void getContext_nullContext() {
    reactorContextHelper = new ReactorContextHelperImpl(Collections.emptyList());
    assertNull(reactorContextHelper.getContext(null, KEY));
  }

  @Test
  void getContext_exception() {
    reactorContextHelper = new ReactorContextHelperImpl(Collections.emptyList());

    Context context = mock(Context.class);
    when(context.hasKey(any())).thenReturn(true);
    when(context.get(any())).thenThrow(RuntimeException.class);

    IllegalStateException e = assertThrows(IllegalStateException.class,
        () -> reactorContextHelper.getContext(context, KEY));
    assertEquals("Failed to get injected reactor context of key " + KEY, e.getMessage());

    assertTrue(e.getCause() instanceof IllegalStateException);
    assertEquals("Failed to get injected reactor context", e.getCause().getMessage());

    assertTrue(e.getCause().getCause() instanceof RuntimeException);
  }

  @SafeVarargs
  private void mockDefaultKey(ReactorContextSetting<Object> ...settings) {
    Arrays.stream(settings).forEach(setting -> {
      when(setting.getKey()).thenReturn(KEY);
    });
  }

  @SafeVarargs
  private void mockDefaultGetter(ReactorContextSetting<Object> ...settings) {
    Arrays.stream(settings).forEach(setting -> {
      //noinspection unchecked
      when(setting.getGetter()).thenReturn(mock(ThreadContextGetter.class,
          AdditionalAnswers.delegatesTo((ThreadContextGetter<Object>) (context, key) -> DATA)));
    });
  }

  @SafeVarargs
  private void mockDefaultSetter(ReactorContextSetting<Object> ...settings) {
    Arrays.stream(settings).forEach(setting -> {
      //noinspection unchecked
      when(setting.getSetter()).thenReturn(mock(ThreadContextSetter.class,
          AdditionalAnswers.delegatesTo((ThreadContextSetter<Object>) (data, context, key) -> {})));
    });
  }

}
