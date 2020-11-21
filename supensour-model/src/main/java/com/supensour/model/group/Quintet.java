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
public class Quintet<T, R, U, V, W> {

  private T first;

  private R second;

  private U third;

  private V fourth;

  private W fifth;

}
