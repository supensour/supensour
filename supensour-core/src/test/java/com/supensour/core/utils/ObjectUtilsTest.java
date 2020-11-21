package com.supensour.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
class ObjectUtilsTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void splitPath_valid_success() {
    List<String> expectation = Arrays.asList("foo", "bar");
    assertEquals(expectation, ObjectUtils.splitPath("foo/bar", "/"));

    expectation = Arrays.asList("foo", "/bar");
    assertEquals(expectation, ObjectUtils.splitPath("foo/\\/bar", "/"));

    expectation = Arrays.asList("foo", "bar");
    assertEquals(expectation, ObjectUtils.splitPath("foo.bar", "."));

    expectation = Arrays.asList("foo", ".bar");
    assertEquals(expectation, ObjectUtils.splitPath("foo.\\.bar", "."));

    expectation = Collections.singletonList("foo.bar");
    assertEquals(expectation, ObjectUtils.splitPath("foo.bar", "/"));

    expectation = Collections.singletonList("foo.\\.bar");
    assertEquals(expectation, ObjectUtils.splitPath("foo.\\.bar", "/"));
  }

  @Test
  void splitPath_emptySeparator_exception() {
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ObjectUtils.splitPath("foo/bar", ""));
    assertEquals("Unexpected empty separator", e.getMessage());
  }

  @Test
  void traverseAndFind_object_nullObject_success() {
    assertNull(ObjectUtils.traverseAndFind((Object) null, Collections.emptyList()));
  }

  @Test
  void traverseAndFind_object_emptyPathParts_success() {
    Map<?, ?> data = Collections.emptyMap();
    assertEquals(data, ObjectUtils.traverseAndFind((Object) data, null));
  }

  @Test
  void traverseAndFind_object_untraversableType_success() {
    int data = 1;
    assertNull(ObjectUtils.traverseAndFind(data, Arrays.asList("foo", "bar")));
  }

  @Test
  void traverseAndFind_object_mapType_success() {
    Map<String, String> data = Collections.singletonMap("foo", "bar");
    List<String> path = ObjectUtils.splitPath("foo", "/");
    assertEquals("bar", ObjectUtils.traverseAndFind((Object) data, path));
  }

  @Test
  void traverseAndFind_object_listType_success() {
    List<String> data = Collections.singletonList("foo");
    List<String> path = ObjectUtils.splitPath("0", "/");
    assertEquals("foo", ObjectUtils.traverseAndFind((Object) data, path));
  }

  @Test
  void traverseAndFind_object_deep_success() throws Exception {
    String json = "{ \"foo\": { \"bar\": [ { \"baz\": \"qux\" } ] } }";
    Object data = objectMapper.readValue(json, Object.class);

    List<String> path = ObjectUtils.splitPath("foo/bar/0/baz", "/");
    assertEquals("qux", ObjectUtils.traverseAndFind(data, path));
  }

  @Test
  void traverseAndFind_map_emptyMap_success() {
    Map<?, ?> data = Collections.emptyMap();
    List<String> path = Collections.emptyList();
    assertEquals(data, ObjectUtils.traverseAndFind(data, path));
  }

  @Test
  void traverseAndFind_map_emptyPathParts_success() {
    Map<String, String> data = Collections.singletonMap("foo", "bar");
    assertEquals(data, ObjectUtils.traverseAndFind(data, null));
  }

  @Test
  void traverseAndFind_map_success() {
    Map<String, String> data = Collections.singletonMap("foo", "bar");
    List<String> path = ObjectUtils.splitPath("foo", "/");
    assertEquals("bar", ObjectUtils.traverseAndFind(data, path));
  }

  @Test
  void traverseAndFind_list_emptyList_success() {
    List<?> data = Collections.emptyList();
    List<String> path = Collections.emptyList();
    assertEquals(data, ObjectUtils.traverseAndFind(data, path));
  }

  @Test
  void traverseAndFind_list_emptyPathParts_success() {
    List<?> data = Arrays.asList(1, 2, 3);
    assertEquals(data, ObjectUtils.traverseAndFind(data, null));
  }

  @Test
  void traverseAndFind_list_success() {
    List<?> data = Arrays.asList(1, 2, 3);
    List<String> path = ObjectUtils.splitPath("1", "/");
    assertEquals(2, ObjectUtils.traverseAndFind(data, path));
  }

  @ParameterizedTest
  @ValueSource(strings = { "-1", "3", "foo" })
  void traverseAndFind_list_notFound(String path) {
    List<?> data = Arrays.asList(1, 2, 3);
    List<String> pathList = ObjectUtils.splitPath(path, "/");
    assertNull(ObjectUtils.traverseAndFind(data, pathList));
  }

}
