package com.supensour.core.utils;

import com.supensour.model.common.PagingRequest;
import com.supensour.model.common.PagingResponse;
import com.supensour.model.common.SortingRequest;
import com.supensour.model.enums.SortingDirection;
import com.supensour.model.enums.SortingNullHandling;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PagingUtils {

  public static Pageable toPageable(PagingRequest page) {
    return toPageable(page, (List<SortingRequest>) null);
  }

  public static Pageable toPageable(PagingRequest page, SortingRequest sortingRequest) {
    List<SortingRequest> sorting = Optional.ofNullable(sortingRequest)
        .map(Collections::singletonList)
        .orElse(null);
    return toPageable(page, sorting);
  }

  public static Pageable toPageable(PagingRequest page, List<SortingRequest> sortingRequests) {
    Assert.notNull(page, "Paging request is null");
    Assert.notNull(page.getNumber(), "Page number is null");
    Assert.notNull(page.getSize(), "Page size is null");
    Sort sort = Optional.ofNullable(sortingRequests)
        .filter(CollectionUtils::isNotEmpty)
        .map(PagingUtils::toOrders)
        .map(Sort::by)
        .orElseGet(Sort::unsorted);
    return PageRequest.of(page.getNumber().intValue(), page.getSize().intValue(), sort);
  }

  public static List<Sort.Order> toOrders(List<SortingRequest> sortRequests) {
    return Optional.ofNullable(sortRequests)
        .stream()
        .flatMap(Collection::stream)
        .map(PagingUtils::toOrder)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  public static Sort.Order toOrder(SortingRequest sorting) {
    boolean valid = Optional.ofNullable(sorting)
        .filter(s -> StringUtils.hasText(s.getField()))
        .isPresent();
    if (!valid) {
      return null;
    }

    Sort.Direction direction = toDirection(sorting.getDirection());
    Sort.NullHandling nullHandling = toNullHandling(sorting.getNullHandling());
    Sort.Order order = new Sort.Order(direction, sorting.getField(), nullHandling);
    if (Boolean.TRUE.equals(sorting.getIgnoreCase())) {
      order = order.ignoreCase();
    }
    return order;
  }

  public static Sort.Direction toDirection(SortingDirection direction) {
    return toDirectionOrElse(direction, Sort.Direction.ASC);
  }

  public static Sort.Direction toDirectionOrElse(SortingDirection direction, Sort.Direction defaultDirection) {
    return Optional.ofNullable(direction)
        .map(Enum::name)
        .flatMap(Sort.Direction::fromOptionalString)
        .orElse(defaultDirection);
  }

  public static Sort.NullHandling toNullHandling(SortingNullHandling nullHandling) {
    return toNullHandlingOrElse(nullHandling, Sort.NullHandling.NATIVE);
  }

  public static Sort.NullHandling toNullHandlingOrElse(SortingNullHandling nullHandling,
                                                       Sort.NullHandling defaultNullHandling) {
    return Optional.ofNullable(nullHandling)
        .map(SortingNullHandling::name)
        .map(Sort.NullHandling::valueOf)
        .orElse(defaultNullHandling);
  }

  public static PagingResponse toPagingResponse(Page<?> page) {
    return PagingResponse.builder()
        .number((long) page.getNumber())
        .size((long) page.getSize())
        .numberOfElements((long) page.getNumberOfElements())
        .totalPages((long) page.getTotalPages())
        .totalElements(page.getTotalElements())
        .build();
  }

  public static PagingResponse toPagingResponse(PagingRequest pagingRequest, Long numberOfElements, Long totalPages, Long totalElements) {
    return PagingResponse.builder()
        .number(pagingRequest.getNumber())
        .size(pagingRequest.getSize())
        .numberOfElements(numberOfElements)
        .totalPages(totalPages)
        .totalElements(totalElements)
        .build();
  }

}
