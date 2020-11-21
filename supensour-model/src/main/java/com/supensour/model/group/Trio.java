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
public class Trio<T, R, U> {

  private T first;

  private R second;

  private U third;

}
