package com.supensour.reactor.context;

import com.supensour.model.annotation.Experimental;
import com.supensour.reactor.context.handler.ReactorContextAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Inherited
@Documented
@Experimental
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ReactorContextAutoConfiguration.class)
public @interface EnableReactorContextInjection {

}
