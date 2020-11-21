package com.supensour.validation.beanvalidation;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public interface Validator {

  <T> void validate(T request, Class<?> ...groups);

}
