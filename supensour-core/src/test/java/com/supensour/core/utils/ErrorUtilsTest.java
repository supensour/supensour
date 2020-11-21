package com.supensour.core.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.supensour.core.sample.dto.Name;
import com.supensour.core.sample.dto.NameRequest;
import com.supensour.core.sample.dto.Numbers;
import com.supensour.model.constant.ErrorCodes;
import com.supensour.model.constant.FieldNames;
import com.supensour.model.map.ListValueMap;
import com.supensour.model.map.impl.DefaultListValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebInputException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@ExtendWith(MockitoExtension.class)
class ErrorUtilsTest {

  private Validator validator;

  private ObjectMapper objectMapper;

  @Mock
  private MethodParameter methodParameter;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    objectMapper = new ObjectMapper();
  }

  @Test
  void mapBindingResult() {
    BindingResult bindingResult = new BeanPropertyBindingResult(null, "nameRequest");
    bindingResult.addError(new FieldError("nameRequest", "firstName", "Blank"));
    bindingResult.addError(new FieldError("nameRequest", "lastName", "Blank"));

    Map<String, List<String>> errors = ErrorUtils.mapBindingResult(bindingResult);
    assertFalse(errors.isEmpty());
    assertEquals(2, errors.size());
    assertEquals(1, errors.get("firstName").size());
    assertEquals("Blank", errors.get("firstName").get(0));
    assertEquals(1, errors.get("lastName").size());
    assertEquals("Blank", errors.get("lastName").get(0));
  }

  @Test
  void mapBindingResult_empty() {
    BindingResult bindingResult = new BeanPropertyBindingResult(null, "nameRequest");
    Map<String, List<String>> errors = ErrorUtils.mapBindingResult(bindingResult);
    assertTrue(errors.isEmpty());
  }

  @Test
  void mapServerWebInputException_TypeMismatchException() {
    TypeMismatchException cause = new TypeMismatchException((Object) null, Integer.class);
    ServerWebInputException e = new ServerWebInputException("Invalid param", null, cause);
    ListValueMap<String, String> errors = ErrorUtils.mapServerWebInputException(e);
    assertEquals(1, errors.size());
    assertEquals(1, errors.get(FieldNames.UNKNOWN).size());
    assertEquals(ErrorCodes.INVALID_TYPE, errors.getFirst(FieldNames.UNKNOWN));
  }

  @Test
  void mapServerWebInputException_DecodingException_InvalidFormatException() {
    when(methodParameter.getParameterName()).thenReturn("number1");
    InvalidFormatException cause2 = assertThrows(InvalidFormatException.class, () -> objectMapper
        .readValue("{\"number1\":\"test\"}", Numbers.class));
    DecodingException cause1 = new DecodingException("Failed to decode", cause2);
    ServerWebInputException e = new ServerWebInputException("Invalid body", methodParameter, cause1);
    ListValueMap<String, String> errors = ErrorUtils.mapServerWebInputException(e);
    assertEquals(1, errors.size());
    assertEquals(2, errors.get("number1").size());
    assertTrue(errors.get("number1").stream().anyMatch(code -> code.contains("Cannot deserialize")));
    assertTrue(errors.get("number1").stream().anyMatch(ErrorCodes.INVALID::equals));
  }

  @Test
  void mapServerWebInputException_DecodingException_JsonParseException() {
    JsonParseException cause2 = assertThrows(JsonParseException.class, () -> objectMapper
        .readValue("{test}", Numbers.class));
    DecodingException cause1 = new DecodingException("Failed to decode", cause2);
    ServerWebInputException e = new ServerWebInputException("Invalid body", null, cause1);
    ListValueMap<String, String> errors = ErrorUtils.mapServerWebInputException(e);
    assertEquals(1, errors.size());
    assertEquals(2, errors.get(FieldNames.UNKNOWN).size());
    assertTrue(errors.get(FieldNames.UNKNOWN).stream().anyMatch(code -> code.contains("Unexpected character ('t'")));
    assertTrue(errors.get(FieldNames.UNKNOWN).stream().anyMatch(ErrorCodes.INVALID::equals));
  }

  @Test
  void mapServerWebInputException_DecodingException_IllegalStateException() {
    IllegalStateException cause2 = new IllegalStateException();
    DecodingException cause1 = new DecodingException("Failed to decode", cause2);
    ServerWebInputException e = new ServerWebInputException("Invalid body", null, cause1);
    ListValueMap<String, String> errors = ErrorUtils.mapServerWebInputException(e);
    assertEquals(1, errors.size());
    assertEquals(2, errors.get(FieldNames.UNKNOWN).size());
    assertTrue(errors.get(FieldNames.UNKNOWN).stream().anyMatch("Invalid body"::equals));
    assertTrue(errors.get(FieldNames.UNKNOWN).stream().anyMatch(ErrorCodes.INVALID::equals));
  }

  @Test
  void mapServerWebInputException() {
    when(methodParameter.hasParameterAnnotation(RequestBody.class)).thenReturn(true);
    ServerWebInputException e = new ServerWebInputException("Invalid param", methodParameter);
    ListValueMap<String, String> errors = ErrorUtils.mapServerWebInputException(e);
    assertEquals(1, errors.size());
    assertEquals(2, errors.get(FieldNames.REQUEST_BODY).size());
    assertTrue(errors.get(FieldNames.REQUEST_BODY).stream().anyMatch("Invalid param"::equals));
    assertTrue(errors.get(FieldNames.REQUEST_BODY).stream().anyMatch(ErrorCodes.INVALID::equals));
  }

  @Test
  void mapHttpMessageNotReadableException_JSONProcessingException() {
    JsonParseException cause = assertThrows(JsonParseException.class, () -> objectMapper
        .readValue("{test}", Numbers.class));
    HttpInputMessage httpInputMessage = mock(HttpInputMessage.class);
    HttpMessageNotReadableException e = new HttpMessageNotReadableException("", cause, httpInputMessage);
    ListValueMap<String, String> errors = ErrorUtils.mapHttpMessageNotReadableException(e);
    assertEquals(1, errors.size());
    assertEquals(2, errors.get(FieldNames.DEFAULT).size());
    assertTrue(errors.get(FieldNames.DEFAULT).stream().anyMatch(code -> code.contains("Unexpected character ('t'")));
    assertTrue(errors.get(FieldNames.DEFAULT).stream().anyMatch(ErrorCodes.INVALID::equals));
  }

  @Test
  void mapHttpMessageNotReadableException() {
    HttpInputMessage httpInputMessage = mock(HttpInputMessage.class);
    HttpMessageNotReadableException e = new HttpMessageNotReadableException("Invalid param", httpInputMessage);
    ListValueMap<String, String> errors = ErrorUtils.mapHttpMessageNotReadableException(e);
    assertEquals(1, errors.size());
    assertEquals(1, errors.get(FieldNames.DEFAULT).size());
    assertEquals("Invalid param", errors.get(FieldNames.DEFAULT).get(0));
  }

  @Test
  void mapConstraintViolations() {
    NameRequest nameRequest = new NameRequest(new Name("  \t\n  ", ""));
    Set<ConstraintViolation<NameRequest>> violations = validator.validate(nameRequest);
    Map<String, List<String>> errors = ErrorUtils.mapConstraintViolations(violations);

    ListValueMap<String, String> expectation = new DefaultListValueMap<>();
    expectation.set("name", "InvalidName");
    expectation.set("customNamePath", "InvalidName");
    expectation.set("name.firstName", "Blank");
    expectation.set("name.lastName", "Blank");

    assertFalse(violations.isEmpty());
    assertFalse(errors.isEmpty());
    assertEquals(4, errors.size());
    assertEquals(expectation, errors);
  }

  @Test
  void mapConstraintViolations_empty() {
    NameRequest nameRequest = new NameRequest(new Name("Suprayan", "Yapura"));
    Set<ConstraintViolation<NameRequest>> violations = validator.validate(nameRequest);

    assertTrue(violations.isEmpty());
    Map<String, List<String>> errors = ErrorUtils.mapConstraintViolations(violations);
    assertTrue(errors.isEmpty());
  }

  @Test
  void getPath() {
    ServerWebInputException e = new ServerWebInputException("", null);
    assertEquals(FieldNames.UNKNOWN, ErrorUtils.getPath(e));

    e = new ServerWebInputException("", methodParameter);
    when(methodParameter.hasParameterAnnotation(RequestBody.class)).thenReturn(true);
    assertEquals(FieldNames.REQUEST_BODY, ErrorUtils.getPath(e));
    when(methodParameter.hasParameterAnnotation(RequestBody.class)).thenReturn(false);

    when(methodParameter.getParameterName()).thenReturn("id");
    assertEquals("id", ErrorUtils.getPath(e));

    when(methodParameter.getParameterName()).thenReturn(null);
    assertEquals(FieldNames.UNKNOWN, ErrorUtils.getPath(e));
  }

  @Test
  void getErrorCode() {
    String[] codes = { "typeMismatch", "invalid" };
    FieldError fieldError = new FieldError("user", "id", null, true, codes, null, null);
    assertEquals(ErrorCodes.INVALID_TYPE, ErrorUtils.getErrorCode(fieldError));

    fieldError = new FieldError("user", "id", "Inappropriate");
    assertEquals("Inappropriate", ErrorUtils.getErrorCode(fieldError));
  }

}
