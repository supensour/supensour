package com.supensour.core.thread.function;

import java.util.Map;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@FunctionalInterface
public interface ThreadContextGetter<T> {

  T get(Map<Object, Object> context, Object key);

}
