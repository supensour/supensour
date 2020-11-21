package com.supensour.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * For any element annotated with this {@link Experimental} annotation:
 * 1. It has an experimental maturity.
 * 2. It's subject to change or could even be removed without any notice in advance.
 * 3. It's not suggested for production build.
 *
 * Please use with caution!
 *
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE })
public @interface Experimental {

}
