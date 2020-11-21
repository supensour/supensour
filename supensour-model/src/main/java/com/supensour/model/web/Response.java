package com.supensour.model.web;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.supensour.model.common.PagingResponse;
import com.supensour.model.map.ListValueMap;
import com.supensour.model.map.impl.DefaultListValueMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {

  private static final long serialVersionUID = -3323115010740040651L;

  private Integer code;

  private String status;

  private T data;

  @Builder.Default
  private ListValueMap<String, String> errors = new DefaultListValueMap<>();

  private PagingResponse page;

  private String requestId;

  // JSON Configurations -----

  @JsonGetter("success")
  public boolean isSuccess() {
    return code != null && code >= 100 && code < 400;
  }

  @JsonGetter("errors")
  public Map<String, List<String>> getNativeErrorMap() {
    return errors.toMap();
  }

  @JsonSetter(value = "errors")
  public void setNativeErrorMap(Map<String, List<String>> errors) {
    this.errors = Optional.ofNullable(errors)
        .map(DefaultListValueMap::new)
        .orElseGet(DefaultListValueMap::new);
  }

}
