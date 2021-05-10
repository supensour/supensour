package com.supensour.core.thread.task;

import com.supensour.core.thread.setting.ContextAwareThreadSetting;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public class ContextAwareThreadTask<T> {

  @NonNull
  private final Callable<T> task;

  @NonNull
  private final Map<Object, Object> context;

  @NonNull
  private final List<ContextAwareThreadSetting<Object>> settings;

  public ContextAwareThreadTask(@NonNull Callable<T> task, @NonNull List<ContextAwareThreadSetting<Object>> settings) {
    this.task = task;
    this.context = new HashMap<>();
    this.settings = settings;
    storeContext();
  }

  public final T execute() throws Exception {
    initializeContext();
    try {
      return task.call();
    } finally {
      clearContext();
    }
  }

  private void storeContext() {
    for (ContextAwareThreadSetting<Object> setting : settings) {
      Object key = setting.getKey();
      Optional.ofNullable(setting.getGetter())
          .map(getter -> getter.get(context, key))
          .ifPresent(data -> context.put(key, data));
    }
  }

  private void initializeContext() {
    for (ContextAwareThreadSetting<Object> setting : settings) {
      Object key = setting.getKey();
      Object data = context.get(key);
      Optional.ofNullable(setting.getSetter())
          .ifPresent(setter -> setter.set(data, context, key));
    }
  }

  private void clearContext() {
    for (ContextAwareThreadSetting<Object> setting : settings) {
      Object key = setting.getKey();
      Object data = context.get(key);
      Optional.ofNullable(setting.getDestroyer())
          .ifPresent(destroyer -> destroyer.destroy(data, context, key));
    }
  }

}
