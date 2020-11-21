package com.supensour.model.enums;

import java.util.Optional;

/**
 * Alternative of org.springframework.data.domain.Sort.Direction from spring-data-commons
 * to avoid dependency with spring-data-commons as it depends on unwanted dependencies.
 *
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public enum SortingDirection {

  ASC, DESC;

  public boolean isAscending() {
    return equals(ASC);
  }

  public boolean isDescending() {
    return equals(DESC);
  }

  public static SortingDirection fromString(String value) throws IllegalArgumentException {
    try {
      return SortingDirection.valueOf(value.toUpperCase());
    } catch (Exception e) {
      throw new IllegalArgumentException(String.format(
          "Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), e);
    }
  }

  public static Optional<SortingDirection> fromOptionalString(String value) {
    try {
      return Optional.of(fromString(value));
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

}
