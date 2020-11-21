package com.supensour.core.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@DisplayName("AnnotationUtilsTest")
class AnnotationUtilsTest {

  @Test
  void findUpToClass_methodParameter_onParameter() throws Exception {
    Method method = AnnotationUtilsTest.class.getDeclaredMethod("nonNullParameterMethod", Object.class);
    MethodParameter parameter = MethodParameter.forExecutable(method, 0);
    assertTrue(AnnotationUtils.findUpToClass(parameter, NonNull.class).isPresent());
  }

  @Test
  void findUpToClass_methodParameter_onMethod() throws Exception {
    Method method = AnnotationUtilsTest.class.getDeclaredMethod("nonNullReturnValueMethod", Object.class);
    MethodParameter parameter = MethodParameter.forExecutable(method, 0);
    assertTrue(AnnotationUtils.findUpToClass(parameter, NonNull.class).isPresent());
  }

  @Test
  void findUpToClass_methodParameter_onClass() throws Exception {
    Method method = AnnotationUtilsTest.class.getDeclaredMethod("nonNullReturnValueMethod", Object.class);
    MethodParameter parameter = MethodParameter.forExecutable(method, 0);
    assertTrue(AnnotationUtils.findUpToClass(parameter, DisplayName.class).isPresent());
  }

  @Test
  void findUpToClass_handlerMethod_onMethod() throws Exception {
    Method method = AnnotationUtilsTest.class.getDeclaredMethod("nonNullReturnValueMethod", Object.class);
    HandlerMethod handlerMethod = new HandlerMethod(this, method);
    assertTrue(AnnotationUtils.findUpToClass(handlerMethod, NonNull.class).isPresent());
  }

  @SuppressWarnings("unused")
  static void nonNullParameterMethod(@NonNull Object param) {}

  @NonNull
  @SuppressWarnings("unused")
  static void nonNullReturnValueMethod(Object params) {}

}
