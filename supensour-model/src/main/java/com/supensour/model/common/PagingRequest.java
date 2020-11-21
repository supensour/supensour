package com.supensour.model.common;

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
public class PagingRequest implements Serializable {

  private static final long serialVersionUID = -7001833803259552313L;

  private Long number;

  private Long size;

}
