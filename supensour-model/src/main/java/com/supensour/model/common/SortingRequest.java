package com.supensour.model.common;

import com.supensour.model.enums.SortingDirection;
import com.supensour.model.enums.SortingNullHandling;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SortingRequest implements Serializable {

  private static final long serialVersionUID = -183255449908969913L;

  private SortingDirection direction;

  private String field;

  @Builder.Default
  private Boolean ignoreCase = Boolean.FALSE;

  @Builder.Default
  private SortingNullHandling nullHandling = SortingNullHandling.NATIVE;

}
