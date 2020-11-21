package com.supensour.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorTolerantUtils {

  public static void execute(@NonNull Runnable runnable) {
    execute(runnable, null);
  }

  public static void execute(@NonNull Runnable runnable, Consumer<Exception> onError) {
    try {
      runnable.run();
    } catch (Exception e) {
      Optional.ofNullable(onError)
          .ifPresent(consumer -> consumer.accept(e));
    }
  }

  public static <T> T execute(@NonNull Callable<T> callable) {
    return executeOr(callable, null);
  }

  public static <T> T execute(@NonNull Callable<T> callable, Consumer<Exception> onError) {
    return executeOr(callable, e -> {
      Optional.ofNullable(onError)
          .ifPresent(consumer -> consumer.accept(e));
      return null;
    });
  }

  public static <T> T executeOr(@NonNull Callable<T> callable, Function<Exception, T> onErrorResultProvider) {
    try {
      return callable.call();
    } catch (Exception e) {
      return Optional.ofNullable(onErrorResultProvider)
          .map(provider -> provider.apply(e))
          .orElse(null);
    }
  }

}
