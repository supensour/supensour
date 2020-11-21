package com.supensour.core.thread.executor;

import com.supensour.core.thread.setting.ContextAwareThreadSetting;
import com.supensour.core.thread.task.ContextAwareCallable;
import com.supensour.core.thread.task.ContextAwareRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Slf4j
@SuppressWarnings("serial")
public class ContextAwareThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

  private final List<ContextAwareThreadSetting<Object>> settings = new ArrayList<>();

  public ContextAwareThreadPoolTaskExecutor addSetting(@NonNull ContextAwareThreadSetting<?> setting) {
    Assert.notNull(setting, "Context-aware thread setting is null");
    Assert.notNull(setting.getKey(), "Context-aware thread setting key is null");
    Assert.isTrue(keyIsNotUsed(setting.getKey()), "Context-aware thread setting key has been used");
    //noinspection unchecked
    settings.add((ContextAwareThreadSetting<Object>) setting);
    return this;
  }

  private boolean keyIsNotUsed(Object key) {
    return settings.stream()
        .map(ContextAwareThreadSetting::getKey)
        .noneMatch(key::equals);
  }

  @Override
  public Future<?> submit(Runnable task) {
    return super.submit(new ContextAwareRunnable(task, settings));
  }

  @Override
  public <T> Future<T> submit(Callable<T> task) {
    return super.submit(new ContextAwareCallable<>(task, settings));
  }

  @Override
  public ListenableFuture<?> submitListenable(Runnable task) {
    return super.submitListenable(new ContextAwareRunnable(task, settings));
  }

  @Override
  public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
    return super.submitListenable(new ContextAwareCallable<>(task, settings));
  }

}
