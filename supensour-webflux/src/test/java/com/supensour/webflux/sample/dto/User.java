package com.supensour.webflux.sample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @NotBlank(message = "Blank")
  private String id;

  @NotBlank(message = "Blank")
  private String name;

  @NotNull(message = "Null")
  private Long createdDate;

  private Boolean suspended;

}
