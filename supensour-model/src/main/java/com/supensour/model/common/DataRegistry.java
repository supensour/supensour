package com.supensour.model.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public class DataRegistry<T> {

  protected final List<T> dataList = new ArrayList<>();

  public void register(T data) {
    dataList.add(Objects.requireNonNull(data));
  }

  public List<T> collect() {
    return new ArrayList<>(dataList);
  }

  public static <T> DataRegistry<T> create() {
    return new DataRegistry<>();
  }

}
