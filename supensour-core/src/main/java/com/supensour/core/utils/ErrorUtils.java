package com.supensour.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.supensour.model.constant.ErrorCodes;
import com.supensour.model.constant.FieldNames;
import com.supensour.model.map.ListValueMap;
import com.supensour.model.map.SetValueMap;
import com.supensour.model.map.impl.DefaultListValueMap;
import com.supensour.model.map.impl.DefaultSetValueMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebInputException;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorUtils {

  public static final String ERROR_PATH_SEPARATOR = ".";

  public static ListValueMap<String, String> mapBindingResult(BindingResult bindingResult) {
    return Optional.ofNullable(bindingResult)
        .filter(Errors::hasErrors)
        .map(Errors::getFieldErrors)
        .map(ErrorUtils::mapFieldErrors)
        .orElseGet(DefaultListValueMap::new);
  }

  public static ListValueMap<String, String> mapFieldErrors(Collection<? extends FieldError> fieldErrors) {
    return Optional.ofNullable(fieldErrors)
        .map(errors -> errors.stream()
            .map(fieldError -> new SimpleEntry<>(fieldError.getField(), getErrorCode(fieldError)))
            .collect(DefaultListValueMap<String, String>::new, ListValueMap::add, ListValueMap::addAll))
        .orElseGet(DefaultListValueMap::new);
  }

  public static ListValueMap<String, String> mapJsonProcessingException(JsonProcessingException e, String defaultPath) {
    String path = Optional.ofNullable(getPath(e))
        .filter(StringUtils::hasText)
        .orElse(defaultPath);
    ListValueMap<String, String> errors = new DefaultListValueMap<>();
    errors.addAll(path, Arrays.asList(ErrorCodes.INVALID, e.getOriginalMessage()));
    return errors;
  }

  public static ListValueMap<String, String> mapServerWebInputException(ServerWebInputException e) {
    String path = getPath(e);
    Throwable cause = e.getCause();
    SetValueMap<String, String> errors = new DefaultSetValueMap<>();

    if (cause instanceof TypeMismatchException) {
      errors.add(path, ErrorCodes.INVALID_TYPE);
    } else if (cause instanceof DecodingException && cause.getCause() instanceof JsonProcessingException) {
      errors.add(path, ErrorCodes.INVALID);
      mapJsonProcessingException((JsonProcessingException) cause.getCause(), path).forEach(errors::addAll);
    } else {
      errors.add(path, ErrorCodes.INVALID);
      errors.add(path, e.getReason());
    }

    return new DefaultListValueMap<>(errors);
  }

  public static ListValueMap<String, String> mapHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    SetValueMap<String, String> errors = new DefaultSetValueMap<>();
    Throwable cause = e.getCause();

    if (cause instanceof JsonProcessingException) {
      mapJsonProcessingException((JsonProcessingException) cause, FieldNames.DEFAULT).forEach(errors::addAll);
    } else {
      Optional.ofNullable(e.getMessage()).ifPresent(msg -> errors.add(FieldNames.DEFAULT, msg));
    }

    return new DefaultListValueMap<>(errors);
  }

  public static ListValueMap<String, String> mapConstraintViolations(Collection<? extends ConstraintViolation<?>> constraintViolations) {
    return Optional.ofNullable(constraintViolations)
        .map(errors -> errors.stream()
            .map(ErrorUtils::mapConstraintViolation)
            .collect(DefaultListValueMap<String, String>::new, CollectionUtils::addAllToMultiValueMap, CollectionUtils::addAllToMultiValueMap))
        .orElseGet(DefaultListValueMap::new);
  }

  public static ListValueMap<String, String> mapConstraintViolation(ConstraintViolation<?> constraintViolation) {
    return getPath(constraintViolation).stream()
        .map(path -> new AbstractMap.SimpleEntry<>(path, constraintViolation.getMessage()))
        .collect(DefaultListValueMap::new, ListValueMap::add, ListValueMap::addAll);
  }

  public static List<String> getPath(ConstraintViolation<?> constraintViolation) {
    return getPath(constraintViolation, FieldNames.PATH);
  }

  public static List<String> getPath(ConstraintViolation<?> constraintViolation, String path) {
    String[] paths = (String[]) constraintViolation.getConstraintDescriptor().getAttributes().get(path);
    if (CollectionUtils.isEmpty(paths)) {
      String defaultPath = getDefaultPath(constraintViolation);
      paths = new String[] { defaultPath };
    }
    return CollectionUtils.toList(paths);
  }

  public static String getDefaultPath(ConstraintViolation<?> constraintViolation) {
    return getDefaultPath(constraintViolation, ERROR_PATH_SEPARATOR);
  }

  public static String getDefaultPath(ConstraintViolation<?> constraintViolation, String separator) {
    Path path = constraintViolation.getPropertyPath();
    List<String> nodes = new ArrayList<>();
    path.iterator().forEachRemaining(node -> Optional.ofNullable(node)
        .map(Path.Node::getName)
        .ifPresent(nodes::add));
    return String.join(separator, nodes);
  }

  public static String getPath(JsonProcessingException e) {
    if (e instanceof JsonMappingException) {
      return ((JsonMappingException) e).getPath().stream()
          .map(JsonMappingException.Reference::getFieldName)
          .collect(Collectors.joining(ERROR_PATH_SEPARATOR));
    }
    return "";
  }

  public static String getPath(ServerWebInputException e) {
    MethodParameter parameter = e.getMethodParameter();
    if (parameter == null) {
      return FieldNames.UNKNOWN;
    }

    if (parameter.hasParameterAnnotation(RequestBody.class)) {
      return FieldNames.REQUEST_BODY;
    }

    return Optional.ofNullable(parameter.getParameterName())
        .filter(StringUtils::hasText)
        .orElse(FieldNames.UNKNOWN);
  }

  public static String getErrorCode(FieldError fieldError) {
    List<String> codes = Optional.ofNullable(fieldError.getCodes())
        .map(Arrays::asList)
        .orElseGet(Collections::emptyList);
    if (codes.contains("typeMismatch")) {
      return ErrorCodes.INVALID_TYPE;
    }
    return fieldError.getDefaultMessage();
  }

}
