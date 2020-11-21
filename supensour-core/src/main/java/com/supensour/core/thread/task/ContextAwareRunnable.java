package com.supensour.core.thread.task;

import com.supensour.core.thread.setting.ContextAwareThreadSetting;
import com.supensour.core.utils.ErrorTolerantUtils;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public class ContextAwareRunnable extends ContextAwareThreadTask<Void> implements Runnable {

  public ContextAwareRunnable(@NonNull Runnable task, @NonNull List<ContextAwareThreadSetting<Object>> settings) {
    super(toCallable(task), settings);
  }

  @Override
  public void run() {
    ErrorTolerantUtils.execute(this::execute, e -> {
      throw new IllegalStateException("Task execution throws exception", e);
    });
  }

  private static Callable<Void> toCallable(Runnable runnable) {
    return () -> {
      runnable.run();
      return null;
    };
  }

}
