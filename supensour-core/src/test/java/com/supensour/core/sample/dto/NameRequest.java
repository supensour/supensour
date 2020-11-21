package com.supensour.core.sample.dto;

import com.supensour.core.sample.validation.ValidName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NameRequest {

  @Valid
  @ValidName(path = {"name", "customNamePath"})
  private Name name;

}
