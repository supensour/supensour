package com.supensour.model.map.impl;

import com.supensour.model.map.ListValueMap;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DefaultListValueMapTest {

  @Test
  void constructor() {
    assertNewEmptyMap(new DefaultListValueMap<>(1, 0.5f));
    assertNewEmptyMap(new DefaultListValueMap<>(1));
    assertNewEmptyMap(new DefaultListValueMap<>(new HashMap<>()));
    assertNewEmptyMap(new DefaultListValueMap<>());

    Map<String, List<String>> map = new DefaultListValueMap<>("key", "value");
    assertEquals(1, map.size());
    assertEquals(1, map.get("key").size());
    assertEquals("value", map.get("key").get(0));

    map = new DefaultListValueMap<>("key", Arrays.asList("value1", "value2"));
    assertEquals(1, map.size());
    assertEquals(2, map.get("key").size());
    assertEquals("value1", map.get("key").get(0));
    assertEquals("value2", map.get("key").get(1));
  }

  @Test
  void getFirst() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.add(1, 10);
    map.put(2, new ArrayList<>());
    assertNull(map.getFirst(0));
    assertEquals(10, map.getFirst(1).intValue());
    assertNull(map.getFirst(2));
  }

  @Test
  void getFirstOrDefault() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.add(1, 10);
    map.put(2, new ArrayList<>());
    map.put(3, Collections.singletonList(null));
    assertEquals(20, map.getFirstOrDefault(0, 20).intValue());
    assertEquals(10, map.getFirstOrDefault(1, null).intValue());
    assertEquals(30, map.getFirstOrDefault(2, 30));
    assertNull(map.getFirstOrDefault(3, 40));
  }

  @Test
  void addWithKeyValue() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.add(1, 10);
    assertNull(map.getFirst(0));
    assertEquals(1, map.size());
    assertEquals(10, map.getFirst(1).intValue());
    assertEquals(1, map.get(1).size());
    assertEquals(10, map.get(1).get(0).intValue());
    assertTrue(map.containsKey(1));
    assertTrue(map.containsValue(Collections.singletonList((10))));
  }

  @Test
  void addWithEntry() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.add(new AbstractMap.SimpleEntry<>(1, 10));
    assertNull(map.getFirst(0));
    assertEquals(1, map.size());
    assertEquals(10, map.getFirst(1).intValue());
    assertEquals(1, map.get(1).size());
    assertEquals(10, map.get(1).get(0).intValue());
    assertTrue(map.containsKey(1));
    assertTrue(map.containsValue(Collections.singletonList((10))));
  }

  @Test
  void addWithSingleValueMap() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    Map<Integer, Integer> map2 = new HashMap<>();
    map2.put(1, 10);
    map.add(map2);
    assertNull(map.getFirst(0));
    assertEquals(1, map.size());
    assertEquals(10, map.getFirst(1).intValue());
    assertEquals(1, map.get(1).size());
    assertEquals(10, map.get(1).get(0).intValue());
    assertTrue(map.containsKey(1));
    assertTrue(map.containsValue(Collections.singletonList((10))));
  }

  @Test
  void addAllWithKeyValues() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.addAll(1, Arrays.asList(10, 20));
    assertNull(map.getFirst(0));
    assertEquals(1, map.size());
    assertEquals(10, map.getFirst(1).intValue());
    assertEquals(2, map.get(1).size());
    assertEquals(10, map.get(1).get(0).intValue());
    assertEquals(20, map.get(1).get(1).intValue());
  }

  @Test
  void addAllWithEntry() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.addAll(new AbstractMap.SimpleEntry<>(1, Arrays.asList(10, 20)));
    assertNull(map.getFirst(0));
    assertEquals(1, map.size());
    assertEquals(10, map.getFirst(1).intValue());
    assertEquals(2, map.get(1).size());
    assertEquals(10, map.get(1).get(0).intValue());
    assertEquals(20, map.get(1).get(1).intValue());
  }

  @Test
  void addAllWithListValueMap() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    ListValueMap<Integer, Integer> map2 = new DefaultListValueMap<>();
    map2.add(1, 10);
    map2.add(1, 20);
    map.addAll(map2);
    assertNull(map.getFirst(0));
    assertEquals(1, map.size());
    assertEquals(10, map.getFirst(1).intValue());
    assertEquals(2, map.get(1).size());
    assertEquals(10, map.get(1).get(0).intValue());
    assertEquals(20, map.get(1).get(1).intValue());
  }

  @Test
  void setWithKeyValue() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.set(1, 1);
    map.set(1, 10);
    assertNull(map.getFirst(0));
    assertEquals(1, map.size());
    assertEquals(10, map.getFirst(1).intValue());
    assertEquals(1, map.get(1).size());
    assertEquals(10, map.get(1).get(0).intValue());
  }

  @Test
  void setWithEntry() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.set(1, 1);
    map.set(new AbstractMap.SimpleEntry<>(1, 10));
    assertNull(map.getFirst(0));
    assertEquals(1, map.size());
    assertEquals(10, map.getFirst(1).intValue());
    assertEquals(1, map.get(1).size());
    assertEquals(10, map.get(1).get(0).intValue());
  }

  @Test
  void setWithSingleValueMap() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.set(1, 1);
    Map<Integer, Integer> map2 = new HashMap<>();
    map2.put(1, 10);
    map.set(map2);
    assertNull(map.getFirst(0));
    assertEquals(1, map.size());
    assertEquals(10, map.getFirst(1).intValue());
    assertEquals(1, map.get(1).size());
    assertEquals(10, map.get(1).get(0).intValue());
  }

  @Test
  void setAllWithKeyValues() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.addAll(1, Arrays.asList(10, 20, 30));
    map.setAll(1, Arrays.asList(10, 20));
    assertNull(map.getFirst(0));
    assertEquals(1, map.size());
    assertEquals(10, map.getFirst(1).intValue());
    assertEquals(2, map.get(1).size());
    assertEquals(10, map.get(1).get(0).intValue());
    assertEquals(20, map.get(1).get(1).intValue());
  }

  @Test
  void setAllWithEntry() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.addAll(1, Arrays.asList(10, 20, 30));
    map.setAll(new AbstractMap.SimpleEntry<>(1, Arrays.asList(10, 20)));
    assertNull(map.getFirst(0));
    assertEquals(1, map.size());
    assertEquals(10, map.getFirst(1).intValue());
    assertEquals(2, map.get(1).size());
    assertEquals(10, map.get(1).get(0).intValue());
    assertEquals(20, map.get(1).get(1).intValue());
  }

  @Test
  void setAllWithListValueMap() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.addAll(1, Arrays.asList(10, 20, 30));
    map.addAll(2, Arrays.asList(11, 12, 13));
    ListValueMap<Integer, Integer> map2 = new DefaultListValueMap<>();
    map2.add(1, 10);
    map2.add(1, 20);
    map.setAll(map2);

    assertNull(map.getFirst(0));
    assertEquals(2, map.size());

    assertEquals(10, map.getFirst(1).intValue());
    assertEquals(2, map.get(1).size());
    assertEquals(10, map.get(1).get(0).intValue());
    assertEquals(20, map.get(1).get(1).intValue());

    assertEquals(11, map.getFirst(2).intValue());
    assertEquals(3, map.get(2).size());
    assertEquals(11, map.get(2).get(0).intValue());
    assertEquals(12, map.get(2).get(1).intValue());
    assertEquals(13, map.get(2).get(2).intValue());
  }

  @Test
  void toMap() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.addAll(0, Arrays.asList(1, 2, 3));
    map.addAll(1, Arrays.asList(4, 5, 6));

    Map<Integer, List<Integer>> map2 = map.toMap();
    assertEquals(2, map2.size());
    assertEquals(3, map2.get(0).size());
    assertEquals(1, map2.get(0).get(0).intValue());
    assertEquals(2, map2.get(0).get(1).intValue());
    assertEquals(3, map2.get(0).get(2).intValue());
    assertEquals(3, map2.get(1).size());
    assertEquals(4, map2.get(1).get(0).intValue());
    assertEquals(5, map2.get(1).get(1).intValue());
    assertEquals(6, map2.get(1).get(2).intValue());

    assertEquals(map, map2);
    assertEquals(map2, map);
    assertEquals(map, map);
    assertEquals(map2, map2);
  }

  @Test
  void toSingleValueMap() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.addAll(0, Arrays.asList(1, 2, 3));
    map.addAll(1, Arrays.asList(4, 5, 6));

    Map<Integer, Integer> map2 = map.toSingleValueMap();
    assertEquals(2, map2.size());
    assertEquals(1, map2.get(0).intValue());
    assertEquals(4, map2.get(1).intValue());
  }

  @Test
  void testClone() {
    DefaultListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.addAll(0, Arrays.asList(1, 2, 3));
    map.addAll(1, Arrays.asList(4, 5, 6));

    Map<Integer, List<Integer>> map2 = map.clone();
    assertEquals(2, map2.size());
    assertEquals(3, map2.get(0).size());
    assertEquals(1, map2.get(0).get(0).intValue());
    assertEquals(2, map2.get(0).get(1).intValue());
    assertEquals(3, map2.get(0).get(2).intValue());
    assertEquals(3, map2.get(1).size());
    assertEquals(4, map2.get(1).get(0).intValue());
    assertEquals(5, map2.get(1).get(1).intValue());
    assertEquals(6, map2.get(1).get(2).intValue());

    assertEquals(map, map2);
    assertEquals(map2, map);
    assertEquals(map, map);
    assertEquals(map2, map2);
    assertTrue(map2 instanceof DefaultListValueMap);
  }

  @Test
  void clear() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.addAll(1, Arrays.asList(10, 20, 30));
    map.addAll(2, Arrays.asList(11, 12, 13));
    map.clear();
    assertEquals(0, map.size());
  }

  @Test
  void keySet() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.addAll(1, Arrays.asList(10, 20, 30));
    map.addAll(2, Arrays.asList(11, 12, 13));

    List<Integer> keys = new ArrayList<>(map.keySet());
    assertEquals(2, keys.size());

    assertEquals(1, keys.get(0).intValue());
    assertEquals(2, keys.get(1).intValue());
  }

  @Test
  void values() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.addAll(1, Arrays.asList(10, 20, 30));
    map.addAll(2, Arrays.asList(11, 12, 13));

    List<List<Integer>> values = new ArrayList<>(map.values());
    assertEquals(2, values.size());

    assertEquals(3, values.get(0).size());
    assertEquals(10, values.get(0).get(0).intValue());
    assertEquals(20, values.get(0).get(1).intValue());
    assertEquals(30, values.get(0).get(2).intValue());

    assertEquals(3, values.get(1).size());
    assertEquals(11, values.get(1).get(0).intValue());
    assertEquals(12, values.get(1).get(1).intValue());
    assertEquals(13, values.get(1).get(2).intValue());
  }

  @Test
  void entrySet() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.addAll(1, Arrays.asList(10, 20, 30));
    map.addAll(2, Arrays.asList(11, 12, 13));

    List<Map.Entry<Integer, List<Integer>>> entries = new ArrayList<>(map.entrySet());
    assertEquals(2, entries.size());

    assertEquals(1, entries.get(0).getKey().intValue());
    assertEquals(3, entries.get(0).getValue().size());
    assertEquals(10, entries.get(0).getValue().get(0).intValue());
    assertEquals(20, entries.get(0).getValue().get(1).intValue());
    assertEquals(30, entries.get(0).getValue().get(2).intValue());

    assertEquals(2, entries.get(1).getKey().intValue());
    assertEquals(3, entries.get(1).getValue().size());
    assertEquals(11, entries.get(1).getValue().get(0).intValue());
    assertEquals(12, entries.get(1).getValue().get(1).intValue());
    assertEquals(13, entries.get(1).getValue().get(2).intValue());
  }

  @Test
  void testHashCode() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.addAll(1, Collections.singletonList(10));
    map.addAll(2, Collections.singletonList(11));
    assertEquals(80, map.hashCode());
  }

  @Test
  void testToString() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.addAll(1, Arrays.asList(10, 20));
    map.addAll(2, Arrays.asList(11, 12));
    map.addAll(3, Arrays.asList(11, 12));
    map.remove(3);
    assertEquals("{1=[10, 20], 2=[11, 12]}", map.toString());
  }

  @Test
  void putAll() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.addAll(1, Arrays.asList(10, 20));
    map.addAll(2, Arrays.asList(11, 12));

    ListValueMap<Integer, Integer> mapReplacement = new DefaultListValueMap<>();
    mapReplacement.setAll(2, Arrays.asList(20, 30));

    map.putAll(mapReplacement);
    assertEquals("{1=[10, 20], 2=[20, 30]}", map.toString());
  }

  private void assertNewEmptyMap(ListValueMap<?, ?> map) {
    assertNotNull(map);
    assertEquals(0, map.size());
    assertTrue(map.isEmpty());
  }

}
