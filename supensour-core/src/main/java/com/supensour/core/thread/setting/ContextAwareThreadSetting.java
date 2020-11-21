package com.supensour.core.thread.setting;

import com.supensour.core.thread.function.ThreadContextDestroyer;
import com.supensour.core.thread.function.ThreadContextGetter;
import com.supensour.core.thread.function.ThreadContextSetter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ContextAwareThreadSetting<T> {

  private final Object key;

  private final ThreadContextSetter<T> setter;

  private final ThreadContextGetter<T> getter;

  private final ThreadContextDestroyer<T> destroyer;

}
