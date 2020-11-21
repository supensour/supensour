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
public class Pair<T, R> {

  private T first;

  private R second;

}
