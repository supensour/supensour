package com.supensour.reactor.context;

import com.supensour.core.thread.function.ThreadContextGetter;
import com.supensour.core.thread.function.ThreadContextSetter;
import com.supensour.model.annotation.Experimental;
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
@Experimental
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReactorContextSetting<T> {

  private final Object key;

  private final ThreadContextGetter<T> getter;

  private final ThreadContextSetter<T> setter;

}
