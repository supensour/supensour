package com.supensour.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectUtils {

  public static List<String> splitPath(String path, String separator) {
    String nonNullPath = Optional.ofNullable(path).orElse("");
    Assert.hasLength(separator, "Unexpected empty separator");

    return Arrays.stream(nonNullPath.split(String.format("(?<!\\\\)\\%s", separator.charAt(0))))
        .map(part -> part.replaceAll(String.format("\\\\\\%s", separator.charAt(0)), separator))
        .collect(Collectors.toList());
  }

  public static Object traverseAndFind(Object object, List<String> pathParts) {
    return doTraverseAndFind(object, normalizePathParts(pathParts));
  }

  private static Object doTraverseAndFind(Object object, List<String> pathParts) {
    if (object == null || CollectionUtils.isEmpty(pathParts)) {
      return object;
    }

    if (object instanceof Map) {
      return doTraverseAndFind((Map<?, ?>) object, pathParts);
    }

    if (object instanceof List) {
      return doTraverseAndFind((List<?>) object, pathParts);
    }

    return null;
  }

  public static Object traverseAndFind(Map<?, ?> map, List<String> pathParts) {
    return doTraverseAndFind(map, normalizePathParts(pathParts));
  }

  private static Object doTraverseAndFind(Map<?, ?> map, List<String> pathParts) {
    if (CollectionUtils.isEmpty(map) || CollectionUtils.isEmpty(pathParts)) {
      return map;
    }

    String nextKey = pathParts.remove(0);
    Object nextData = map.get(nextKey);
    return doTraverseAndFind(nextData, pathParts);
  }

  public static Object traverseAndFind(List<?> list, List<String> pathParts) {
    return doTraverseAndFind(list, normalizePathParts(pathParts));
  }

  private static Object doTraverseAndFind(List<?> list, List<String> pathParts) {
    if (CollectionUtils.isEmpty(list) || CollectionUtils.isEmpty(pathParts)) {
      return list;
    }

    String nextKey = pathParts.remove(0);
    Object nextData = Optional.ofNullable(tryParseInt(nextKey))
        .filter(idx -> idx >= 0)
        .filter(idx -> list.size() > idx)
        .map(list::get)
        .orElse(null);
    return doTraverseAndFind(nextData, pathParts);
  }

  private static List<String> normalizePathParts(List<String> pathParts) {
    return Optional.ofNullable(pathParts)
        .orElseGet(ArrayList::new)
        .stream()
        .filter(StringUtils::hasLength)
        .collect(Collectors.toList());
  }

  private static Integer tryParseInt(String str) {
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException e) {
      return null;
    }
  }

}
