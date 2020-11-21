package com.supensour.model.enums;

/**
 * Alternative of org.springframework.data.domain.Sort.NullHandling from spring-data-commons
 * to avoid dependency with spring-data-commons as it depends on unwanted dependencies.
 *
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public enum SortingNullHandling {

  /**
   * Lets the data store decide what to do with nulls.
   */
  NATIVE,

  /**
   * A hint to the used data store to order entries with null values before non null entries.
   */
  NULLS_FIRST,

  /**
   * A hint to the used data store to order entries with null values after non null entries.
   */
  NULLS_LAST;

}
