package com.supensour.reactor.context.handler;

import com.supensour.model.annotation.Experimental;
import com.supensour.reactor.context.ReactorContextHelper;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.util.context.Context;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Experimental
class ReactorContextSubscriber<T> implements CoreSubscriber<T> {

  static final String LIFTER_KEY = ReactorContextSubscriber.class.getName();
  static final Object REACTOR_CONTEXT_KEY = ReactorContextSubscriber.class;

  private final CoreSubscriber<T> delegate;

  private final ReactorContextHelper reactorContextHelper;

  public ReactorContextSubscriber(CoreSubscriber<T> delegate, ReactorContextHelper reactorContextHelper) {
    this.delegate = delegate;
    this.reactorContextHelper = reactorContextHelper;
  }

  @Override
  public Context currentContext() {
    return delegate.currentContext();
  }

  @Override
  public void onSubscribe(Subscription s) {
    delegate.onSubscribe(s);
  }

  @Override
  public void onNext(T t) {
    reactorContextHelper.propagateContext(delegate.currentContext());
    delegate.onNext(t);
  }

  @Override
  public void onError(Throwable throwable) {
    delegate.onError(throwable);
  }

  @Override
  public void onComplete() {
    delegate.onComplete();
  }

}
