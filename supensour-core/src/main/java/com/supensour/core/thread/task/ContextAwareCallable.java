package com.supensour.core.thread.task;

import com.supensour.core.thread.setting.ContextAwareThreadSetting;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public class ContextAwareCallable<T> extends ContextAwareThreadTask<T> implements Callable<T> {

  public ContextAwareCallable(@NonNull Callable<T> task, @NonNull List<ContextAwareThreadSetting<Object>> settings) {
    super(task, settings);
  }

  @Override
  public T call() throws Exception {
    return execute();
  }

}
