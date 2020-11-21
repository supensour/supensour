package com.supensour.core.utils;

import com.supensour.model.group.Pair;
import com.supensour.model.map.ListValueMap;
import com.supensour.model.map.impl.DefaultListValueMap;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
class CollectionUtilsTest {

  @Test
  void isNotEmptyMap() {
    Map<Integer, Integer> map = new HashMap<>();
    map.put(1, 1);
    assertTrue(CollectionUtils.isNotEmpty(map));
  }

  @Test
  void isNotEmptyMultiValueMap() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    map.add(1, 1);
    assertTrue(CollectionUtils.isNotEmpty(map));
  }

  @Test
  void isNotEmptyCollection() {
    List<Integer> list = new ArrayList<>();
    list.add(1);
    assertTrue(CollectionUtils.isNotEmpty(list));
  }

  @Test
  void isNotEmptyIterable() {
    List<Integer> list = new ArrayList<>();
    list.add(1);
    assertTrue(CollectionUtils.isNotEmpty((Iterable<Integer>)list));
  }

  @Test
  void isNotEmptyArray() {
    List<Integer> list = new ArrayList<>();
    list.add(1);
    assertTrue(CollectionUtils.isNotEmpty(list.toArray()));
  }

  @Test
  void isEmptyMap() {
    Map<Integer, Integer> map = new HashMap<>();
    assertTrue(CollectionUtils.isEmpty(map));
    map.put(1, 1);
    assertFalse(CollectionUtils.isEmpty(map));
    map = null;
    //noinspection ConstantConditions
    assertTrue(CollectionUtils.isEmpty(map));
  }

  @Test
  void isEmptyMultiValueMap() {
    ListValueMap<Integer, Integer> map = new DefaultListValueMap<>();
    assertTrue(CollectionUtils.isEmpty(map));
    map.add(1, 1);
    assertFalse(CollectionUtils.isEmpty(map));
    map = null;
    //noinspection ConstantConditions
    assertTrue(CollectionUtils.isEmpty(map));
  }

  @Test
  void isEmptyCollection() {
    List<Integer> list = new ArrayList<>();
    assertTrue(CollectionUtils.isEmpty(list));
    list.add(1);
    assertFalse(CollectionUtils.isEmpty(list));
    list = null;
    //noinspection ConstantConditions
    assertTrue(CollectionUtils.isEmpty(list));
  }

  @Test
  void isEmptyIterable() {
    List<Integer> list = new ArrayList<>();
    assertTrue(CollectionUtils.isEmpty((Iterable<Integer>) list));
    list.add(1);
    assertFalse(CollectionUtils.isEmpty((Iterable<Integer>) list));
    list = null;
    //noinspection ConstantConditions
    assertTrue(CollectionUtils.isEmpty((Iterable<Integer>) list));
  }

  @Test
  void isEmptyArray() {
    List<Integer> list = new ArrayList<>();
    list.add(1);
    assertFalse(CollectionUtils.isEmpty(list.toArray()));
    assertTrue(CollectionUtils.isEmpty(new String[] {}));
    assertTrue(CollectionUtils.isEmpty((Integer[]) null));
  }

  @Test
  void toList() {
    List<String> values = CollectionUtils.toList("A");
    assertEquals(1, values.size());
    assertEquals("A", values.get(0));

    values = CollectionUtils.toList("A", "B", "C");
    assertEquals(3, values.size());
    assertEquals("A", values.get(0));
    assertEquals("B", values.get(1));
    assertEquals("C", values.get(2));
  }

  @Test
  void toSet() {
    Set<String> values = CollectionUtils.toSet("A");
    assertEquals(1, values.size());
    assertEquals("A", values.iterator().next());

    values = CollectionUtils.toSet("A", "B", "A");
    assertEquals(2, values.size());
    Iterator<String> iterator = values.iterator();
    assertEquals("A", iterator.next());
    assertEquals("B", iterator.next());
  }

  @Test
  void addToMapWithPair() {
    Map<Integer, Integer> map = CollectionUtils.addToMap((Map<Integer, Integer>) null, Pair.of(1, 2));
    assertEquals(1, map.size());
    assertEquals(2, map.get(1).intValue());
  }

  @Test
  void addToMapWithEntry() {
    Map<Integer, Integer> map = CollectionUtils.addToMap((Map<Integer, Integer>) null, new AbstractMap.SimpleEntry<>(1, 2));
    assertEquals(1, map.size());
    assertEquals(2, map.get(1).intValue());
  }

  @Test
  @SuppressWarnings("ConstantConditions")
  void addToMapWithKeyValue() {
    Map<String, Integer> map = null;
    CollectionUtils.addToMap(map,"1", 1);
    assertNull(map);

    map = CollectionUtils.addToMap(map, "1", 1);
    assertTrue(map.containsKey("1"));
    assertEquals(1, map.size());
    assertEquals(1, map.get("1").intValue());
  }

  @Test
  void addToMapWithPairToSupplier() {
    Map<Integer, Integer> map = CollectionUtils.addToMap(HashMap::new, Pair.of(1, 2));
    assertEquals(1, map.size());
    assertEquals(2, map.get(1).intValue());
  }

  @Test
  void addToMapWithEntryToSupplier() {
    Map<Integer, Integer> map = CollectionUtils.addToMap(HashMap::new, new AbstractMap.SimpleEntry<>(1, 2));
    assertEquals(1, map.size());
    assertEquals(2, map.get(1).intValue());
  }

  @Test
  void addToMapWithKeyValueToSupplier() {
    Map<String, Integer> map = CollectionUtils.addToMap(HashMap::new, "1", 1);
    assertTrue(map.containsKey("1"));
    assertEquals(1, map.size());
    assertEquals(1, map.get("1").intValue());
  }

  @Test
  void addToMultiValueMapWithPair() {
    Pair<Integer, Integer> pair = Pair.of(1, 2);
    Map<Integer, List<Integer>> map = CollectionUtils.addToMultiValueMap((Map<Integer, List<Integer>>) null, pair);
    assertEquals(1, map.size());
    assertEquals(CollectionUtils.toList(2), map.get(1));
  }

  @Test
  void addToMultiValueMapWithEntry() {
    Map.Entry<Integer, Integer> entry = new AbstractMap.SimpleEntry<>(1, 2);
    Map<Integer, List<Integer>> map = CollectionUtils.addToMultiValueMap((Map<Integer, List<Integer>>) null, entry);
    assertEquals(1, map.size());
    assertEquals(CollectionUtils.toList(2), map.get(1));
  }

  @Test
  void addToMultiValueMapWithSingleValueMap() {
    Map<Integer, Integer> other = new HashMap<>();
    other.put(1, 2);
    Map<Integer, List<Integer>> map = CollectionUtils.addToMultiValueMap((Map<Integer, List<Integer>>) null, other);
    assertEquals(1, map.size());
    assertEquals(CollectionUtils.toList(2), map.get(1));
  }

  @Test
  void addToMultiValueMapWithKeyValue() {
    Map<Integer, List<Integer>> map = CollectionUtils.addToMultiValueMap((Map<Integer, List<Integer>>) null, 1, 2);
    assertEquals(1, map.size());
    assertEquals(CollectionUtils.toList(2), map.get(1));
  }

  @Test
  void addToMultiValueMapWithPairToSupplier() {
    Pair<Integer, Integer> pair = Pair.of(1, 2);
    Map<Integer, List<Integer>> map = CollectionUtils.addToMultiValueMap(HashMap::new, pair);
    assertEquals(1, map.size());
    assertEquals(CollectionUtils.toList(2), map.get(1));
  }

  @Test
  void addToMultiValueMapWithEntryToSupplier() {
    Map.Entry<Integer, Integer> entry = new AbstractMap.SimpleEntry<>(1, 2);
    Map<Integer, List<Integer>> map = CollectionUtils.addToMultiValueMap(HashMap::new, entry);
    assertEquals(1, map.size());
    assertEquals(CollectionUtils.toList(2), map.get(1));
  }

  @Test
  void addToMultiValueMapWithSingleValueMapToSupplier() {
    Map<Integer, Integer> other = new HashMap<>();
    other.put(1, 2);
    Map<Integer, List<Integer>> map = CollectionUtils.addToMultiValueMap(HashMap::new, other);
    assertEquals(1, map.size());
    assertEquals(CollectionUtils.toList(2), map.get(1));
  }

  @Test
  void addToMultiValueMapWithKeyValueToSupplier() {
    Map<Integer, List<Integer>> map = CollectionUtils.addToMultiValueMap(HashMap::new, 1, 2);
    assertEquals(1, map.size());
    assertEquals(CollectionUtils.toList(2), map.get(1));
  }

  @Test
  void addAllToMultiValueMapWithPair() {
    Pair<Integer, Set<Integer>> pair = Pair.of(1, CollectionUtils.toSet(2));
    Map<Integer, List<Integer>> map = CollectionUtils.addAllToMultiValueMap((Map<Integer, List<Integer>>) null, pair);
    assertEquals(1, map.size());
    assertEquals(CollectionUtils.toList(2), map.get(1));
  }

  @Test
  void addAllToMultiValueMapWithEntry() {
    Map.Entry<Integer, Set<Integer>> entry = new AbstractMap.SimpleEntry<>(1, CollectionUtils.toSet(2));
    Map<Integer, List<Integer>> map = CollectionUtils.addAllToMultiValueMap((Map<Integer, List<Integer>>) null, entry);
    assertEquals(1, map.size());
    assertEquals(CollectionUtils.toList(2), map.get(1));
  }

  @Test
  void addAllToMultiValueMapWithMultiValueMap() {
    Map<Integer, Set<Integer>> other = new HashMap<>();
    other.put(1, CollectionUtils.toSet(2));
    Map<Integer, List<Integer>> map = CollectionUtils.addAllToMultiValueMap((Map<Integer, List<Integer>>) null, other);
    assertEquals(1, map.size());
    assertEquals(CollectionUtils.toList(2), map.get(1));
  }

  @Test
  void addAllToMultiValueMapWithKeyValue() {
    Set<Integer> value = new HashSet<>();
    value.add(2);
    Map<Integer, List<Integer>> map = CollectionUtils.addAllToMultiValueMap((Map<Integer, List<Integer>>) null, 1, value);
    assertEquals(1, map.size());
    assertEquals(CollectionUtils.toList(2), map.get(1));
  }

  @Test
  void addAllToMultiValueMapWithPairToSupplier() {
    Pair<Integer, Set<Integer>> pair = Pair.of(1, CollectionUtils.toSet(2));
    Map<Integer, List<Integer>> map = CollectionUtils.addAllToMultiValueMap(HashMap::new, pair);
    assertEquals(1, map.size());
    assertEquals(CollectionUtils.toList(2), map.get(1));
  }

  @Test
  void addAllToMultiValueMapWithEntryToSupplier() {
    Map.Entry<Integer, Set<Integer>> entry = new AbstractMap.SimpleEntry<>(1, CollectionUtils.toSet(2));
    Map<Integer, List<Integer>> map = CollectionUtils.addAllToMultiValueMap(HashMap::new, entry);
    assertEquals(1, map.size());
    assertEquals(CollectionUtils.toList(2), map.get(1));
  }

  @Test
  void addAllToMultiValueMapWithMultiValueMapToSupplier() {
    Map<Integer, Set<Integer>> other = new HashMap<>();
    other.put(1, CollectionUtils.toSet(2));
    Map<Integer, List<Integer>> map = CollectionUtils.addAllToMultiValueMap(HashMap::new, other);
    assertEquals(1, map.size());
    assertEquals(CollectionUtils.toList(2), map.get(1));
  }

  @Test
  void addAllToMultiValueMapWithKeyValueToSupplier() {
    Set<Integer> value = new HashSet<>();
    value.add(2);
    Map<Integer, List<Integer>> map = CollectionUtils.addAllToMultiValueMap(HashMap::new, 1, value);
    assertEquals(1, map.size());
    assertEquals(CollectionUtils.toList(2), map.get(1));
  }

  @Test
  void join() {
    List<String> values1 = CollectionUtils.toList("1", "2");
    List<String> values2 = CollectionUtils.toList("3", "4");
    List<String> result = CollectionUtils.join(values1, values2);

    assertEquals(4, result.size());
    assertEquals("1", result.get(0));
    assertEquals("2", result.get(1));
    assertEquals("3", result.get(2));
    assertEquals("4", result.get(3));

    assertEquals(4, values1.size());
    assertEquals("1", values1.get(0));
    assertEquals("2", values1.get(1));
    assertEquals("3", values1.get(2));
    assertEquals("4", values1.get(3));

    assertEquals(2, values2.size());
    assertEquals("3", values2.get(0));
    assertEquals("4", values2.get(1));
  }

  @Test
  void joinNullTarget() {
    List<String> values1 = CollectionUtils.toList("1", "2");
    List<String> values2 = CollectionUtils.toList("3", "4");

    assertNull(CollectionUtils.join((List<String>) null, values1, values2));

    assertEquals(2, values1.size());
    assertEquals("1", values1.get(0));
    assertEquals("2", values1.get(1));

    assertEquals(2, values2.size());
    assertEquals("3", values2.get(0));
    assertEquals("4", values2.get(1));
  }

  @Test
  void joinToSupplier() {
    List<String> values1 = CollectionUtils.toList("1", "2");
    List<String> values2 = CollectionUtils.toList("3", "4");
    List<String> result = CollectionUtils.join((Supplier<List<String>>) ArrayList::new, values1, values2);

    assertEquals(4, result.size());
    assertEquals("1", result.get(0));
    assertEquals("2", result.get(1));
    assertEquals("3", result.get(2));
    assertEquals("4", result.get(3));

    assertEquals(2, values1.size());
    assertEquals("1", values1.get(0));
    assertEquals("2", values1.get(1));

    assertEquals(2, values2.size());
    assertEquals("3", values2.get(0));
    assertEquals("4", values2.get(1));
  }

  @Test
  void joinMaps() {
    Map<Integer, Integer> values1 = CollectionUtils.addToMap(HashMap::new, 0, 1);
    Map<Integer, Integer> values2 = CollectionUtils.addToMap(HashMap::new, 1, 2);
    Map<Integer, Integer> values3 = CollectionUtils.addToMap(HashMap::new, 1, 3);
    Map<Integer, Integer> values4 = CollectionUtils.addToMap(HashMap::new, 2, 4);
    Map<Integer, Integer> result = CollectionUtils.joinMaps(values1, values2, values3, values4);

    assertEquals(3, result.size());
    assertEquals(1, result.get(0).intValue());
    assertEquals(3, result.get(1).intValue());
    assertEquals(4, result.get(2).intValue());

    assertEquals(3, values1.size());
    assertEquals(1, values1.get(0).intValue());
    assertEquals(3, values1.get(1).intValue());
    assertEquals(4, values1.get(2).intValue());

    assertEquals(1, values2.size());
    assertEquals(2, values2.get(1).intValue());

    assertEquals(1, values3.size());
    assertEquals(3, values3.get(1).intValue());

    assertEquals(1, values4.size());
    assertEquals(4, values4.get(2).intValue());
  }

  @Test
  void joinMapsNullTarget() {
    Map<Integer, Integer> values1 = CollectionUtils.addToMap(HashMap::new, 0, 1);
    Map<Integer, Integer> values2 = CollectionUtils.addToMap(HashMap::new, 1, 2);

    assertNull(CollectionUtils.joinMaps((Map<Integer, Integer>) null, values1, values2));

    assertEquals(1, values1.size());
    assertEquals(1, values1.get(0).intValue());

    assertEquals(1, values2.size());
    assertEquals(2, values2.get(1).intValue());
  }

  @Test
  void joinMapsToSupplier() {
    Map<Integer, Integer> values1 = CollectionUtils.addToMap(HashMap::new, 0, 1);
    Map<Integer, Integer> values2 = CollectionUtils.addToMap(HashMap::new, 1, 2);
    Map<Integer, Integer> values3 = CollectionUtils.addToMap(HashMap::new, 1, 3);
    Map<Integer, Integer> values4 = CollectionUtils.addToMap(HashMap::new, 2, 4);
    Map<Integer, Integer> result = CollectionUtils.joinMaps((Supplier<Map<Integer, Integer>>) HashMap::new, values1, values2, values3, values4);

    assertEquals(3, result.size());
    assertEquals(1, result.get(0).intValue());
    assertEquals(3, result.get(1).intValue());
    assertEquals(4, result.get(2).intValue());

    assertEquals(1, values1.size());
    assertEquals(1, values1.get(0).intValue());

    assertEquals(1, values2.size());
    assertEquals(2, values2.get(1).intValue());

    assertEquals(1, values3.size());
    assertEquals(3, values3.get(1).intValue());

    assertEquals(1, values4.size());
    assertEquals(4, values4.get(2).intValue());
  }

  @Test
  void isUnmodifiable_set() {
    Set<Object> set = new HashSet<>();
    assertTrue(CollectionUtils.isUnmodifiable(Collections.unmodifiableSet(set)));
    assertFalse(CollectionUtils.isUnmodifiable(set));
  }

  @Test
  void isUnmodifiable_list() {
    List<Object> list = new ArrayList<>();
    assertTrue(CollectionUtils.isUnmodifiable(Collections.unmodifiableList(list)));
    assertFalse(CollectionUtils.isUnmodifiable(list));
  }

  @Test
  void isUnmodifiable_map() {
    Map<Object, Object> map = new HashMap<>();
    assertTrue(CollectionUtils.isUnmodifiable(Collections.unmodifiableMap(map)));
    assertFalse(CollectionUtils.isUnmodifiable(map));
  }

  @Test
  void unmodifiable_set() {
    Set<Object> set = CollectionUtils.unmodifiable(new HashSet<>());
    assertTrue(CollectionUtils.isUnmodifiable(set));
    //noinspection ConstantConditions
    assertThrows(UnsupportedOperationException.class, () -> set.add(null));
  }

  @Test
  void unmodifiable_list() {
    List<Object> list = CollectionUtils.unmodifiable(new ArrayList<>());
    assertTrue(CollectionUtils.isUnmodifiable(list));
    //noinspection ConstantConditions
    assertThrows(UnsupportedOperationException.class, () -> list.add(null));
  }

  @Test
  void unmodifiable_map() {
    Map<Object, Object> map = CollectionUtils.unmodifiable(new HashMap<>());
    assertTrue(CollectionUtils.isUnmodifiable(map));
    //noinspection ConstantConditions
    assertThrows(UnsupportedOperationException.class, () -> map.put(null, null));
  }

}
