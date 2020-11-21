package com.supensour.validation.beanvalidation.validation;

import com.supensour.validation.beanvalidation.validation.validator.ShouldBeEmptyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {TYPE, FIELD, METHOD, PARAMETER})
@Constraint(validatedBy = {
    ShouldBeEmptyValidator.class
})
public @interface ShouldBeEmpty {

  String message() default "NotEmpty";

  String[] path() default {};

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
