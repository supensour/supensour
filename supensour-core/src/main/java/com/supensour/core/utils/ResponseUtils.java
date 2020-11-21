package com.supensour.core.utils;

import com.supensour.model.common.PagingResponse;
import com.supensour.model.map.ListValueMap;
import com.supensour.model.web.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.function.Function;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtils {

  public static <T> Response<T> status(HttpStatus status, String requestId, T data, PagingResponse page) {
    return Response.<T>builder()
        .data(data)
        .code(status.value())
        .status(status.name())
        .page(page)
        .requestId(requestId)
        .build();
  }

  public static <T> Response<T> status(HttpStatus status, String requestId, T data, ListValueMap<String, String> errors) {
    Response<T> response = status(status, requestId, data, (PagingResponse) null);
    response.setErrors(errors);
    return response;
  }

  public static <T> Response<T> status(HttpStatus status, String requestId, T data) {
    return status(status, requestId, data, (PagingResponse) null);
  }

  public static <T> Response<T> status(HttpStatus status, String requestId) {
    return status(status, requestId, (T) null);
  }

  public static <T> Response<T> status(HttpStatus status, String requestId, ListValueMap<String, String> errors) {
    return status(status, requestId, null, errors);
  }

  public static <T, R> Response<T> status(HttpStatus status, String requestId, Page<R> page, Function<Page<R>, T> mapper) {
    return status(status, requestId, mapper.apply(page), PagingUtils.toPagingResponse(page));
  }

  public static <T> Response<T> ok(String requestId, T data, PagingResponse page) {
    return status(HttpStatus.OK, requestId, data, page);
  }

  public static <T> Response<T> ok(String requestId, T data) {
    return ok(requestId, data, null);
  }

  public static <T> Response<T> ok(String requestId) {
    return ok(requestId, null);
  }

  public static <T, R> Response<T> ok(String requestId, Page<R> page, Function<Page<R>, T> mapper) {
    return status(HttpStatus.OK, requestId, page, mapper);
  }

  public static <T> Response<T> badRequest(String requestId, ListValueMap<String, String> errors, T data) {
    return status(HttpStatus.BAD_REQUEST, requestId, data, errors);
  }

  public static <T> Response<T> badRequest(String requestId, ListValueMap<String, String> errors) {
    return badRequest(requestId, errors, null);
  }

}
