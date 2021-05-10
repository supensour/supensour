package com.supensour.reactor.context.handler;

import com.supensour.model.annotation.Experimental;
import com.supensour.reactor.context.ReactorContextHelper;
import com.supensour.reactor.context.ReactorContextSetting;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import reactor.util.context.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Experimental
class ReactorContextHelperImpl implements ReactorContextHelper {

  private final List<ReactorContextSetting<Object>> settings;

  ReactorContextHelperImpl(List<ReactorContextSetting<Object>> settings) {
    this.settings = settings;
  }

  @Override
  public Context injectContext(Context context) {
    Map<Object, Object> injectedContext = new HashMap<>();
    for (ReactorContextSetting<Object> setting : settings) {
      Optional.ofNullable(setting.getGetter())
          .map(getter -> getter.get(injectedContext, setting.getKey()))
          .ifPresent(value -> injectedContext.put(setting.getKey(), value));
    }
    return setInjectedContext(context, injectedContext);
  }

  @Override
  public void propagateContext(Context context) {
    Optional<Map<Object, Object>> injectedContext = getInjectedContext(context);
    for (ReactorContextSetting<Object> setting : settings) {
      if (setting.getSetter() == null) {
        continue;
      }
      Object data = injectedContext
          .map(ctx -> ctx.get(setting.getKey()))
          .orElse(null);
      setting.getSetter().set(data, injectedContext.orElse(null), setting.getKey());
    }
  }

  @Override
  public <T> T getContext(Context context, @NonNull Object key) {
    Assert.notNull(key, "Can't get context with null key");
    try {
      //noinspection unchecked
      return (T) getInjectedContext(context)
          .map(ctx -> ctx.get(key))
          .orElse(null);
    } catch (Exception e) {
      var message = String.format("Failed to get injected reactor context of key %s", key);
      throw new IllegalStateException(message, e);
    }
  }

  private Optional<Map<Object, Object>> getInjectedContext(Context context) {
    try {
      return Optional.ofNullable(context)
          .filter(ctx -> ctx.hasKey(ReactorContextSubscriber.REACTOR_CONTEXT_KEY))
          .map(ctx -> ctx.get(ReactorContextSubscriber.REACTOR_CONTEXT_KEY));
    } catch (Exception e) {
      throw new IllegalStateException("Failed to get injected reactor context", e);
    }
  }

  private Context setInjectedContext(Context context, Map<Object, Object> injectedContext) {
    return Optional.ofNullable(context)
        .orElseGet(Context::empty)
        .put(ReactorContextSubscriber.REACTOR_CONTEXT_KEY, injectedContext);
  }

}
