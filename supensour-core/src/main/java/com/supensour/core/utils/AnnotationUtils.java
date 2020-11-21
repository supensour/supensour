package com.supensour.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnnotationUtils {

  public static <T extends Annotation> Optional<T> findUpToClass(MethodParameter parameter, Class<T> annotationType) {
    Optional<MethodParameter> optionalParameter = Optional.ofNullable(parameter);
    return optionalParameter
        .map(p -> p.getParameterAnnotation(annotationType))
        .or(() -> optionalParameter
            .flatMap(p -> findUpToClass(p.getMethod(), annotationType)));
  }

  public static <T extends Annotation> Optional<T> findUpToClass(HandlerMethod handlerMethod, Class<T> annotationType) {
    return Optional.ofNullable(handlerMethod)
        .map(HandlerMethod::getMethod)
        .flatMap(method -> findUpToClass(method, annotationType));
  }

  public static <T extends Annotation> Optional<T> findUpToClass(Method method, Class<T> annotationType) {
    Optional<Method> optionalMethod = Optional.ofNullable(method);
    return optionalMethod
        .map(m -> m.getAnnotation(annotationType))
        .or(() -> optionalMethod
            .flatMap(m -> findAnnotation(m.getDeclaringClass(), annotationType)));
  }

  public static <T extends Annotation> Optional<T> findAnnotation(Class<?> clazz, Class<T> annotationType) {
    return Optional.ofNullable(clazz)
        .map(t -> t.getAnnotation(annotationType));
  }

}
