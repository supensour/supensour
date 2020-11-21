package com.supensour.model.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@Data
@NoArgsConstructor(staticName = "empty")
@AllArgsConstructor(staticName = "of")
public class Quartet<T, R, U, V> {

  private T first;

  private R second;

  private U third;

  private V fourth;

}
