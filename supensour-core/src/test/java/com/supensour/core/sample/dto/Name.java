package com.supensour.core.sample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Name {

  @NotBlank(message = "Blank")
  private String firstName;

  @NotBlank(message = "Blank")
  private String lastName;

}
