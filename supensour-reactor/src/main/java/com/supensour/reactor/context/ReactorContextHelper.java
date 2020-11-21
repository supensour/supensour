package com.supensour.reactor.context;

import com.supensour.model.annotation.Experimental;
import org.springframework.lang.NonNull;
import reactor.util.context.Context;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Experimental
public interface ReactorContextHelper {

  Context injectContext(Context context);

  void propagateContext(Context context);

  <T> T getContext(Context context, @NonNull Object key);

}
