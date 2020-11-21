package com.supensour.model.map.impl;

import com.supensour.model.map.SetValueMap;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Suprayan Yapura
 * @since 1.0.0
 */
class DefaultSetValueMapTest {

   @Test
   void constructor() {
     assertNewEmptyMap(new DefaultSetValueMap<>(1, 0.5f));
     assertNewEmptyMap(new DefaultSetValueMap<>(1));
     assertNewEmptyMap(new DefaultSetValueMap<>(new HashMap<>()));
     assertNewEmptyMap(new DefaultSetValueMap<>());

     Map<String, Set<String>> map = new DefaultSetValueMap<>("key", "value");
     assertEquals(1, map.size());
     assertEquals(1, map.get("key").size());
     assertEquals("value", map.get("key").stream().findFirst().orElse(null));

     map = new DefaultSetValueMap<>("key", Arrays.asList("value1", "value2"));
     assertEquals(1, map.size());
     assertEquals(2, map.get("key").size());
     assertEquals("value1", map.get("key").stream().findFirst().orElse(null));
     assertEquals("value2", map.get("key").stream().skip(1).findFirst().orElse(null));
   }

   @Test
   void getFirst() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.add(1, 10);
     map.put(2, new HashSet<>());
     assertNull(map.getFirst(0));
     assertEquals(10, map.getFirst(1).intValue());
     assertNull(map.getFirst(2));
   }

  @Test
  void getFirstOrDefault() {
    SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
    map.add(1, 10);
    map.put(2, new HashSet<>());
    map.put(3, Collections.singleton(null));
    assertEquals(20, map.getFirstOrDefault(0, 20).intValue());
    assertEquals(10, map.getFirstOrDefault(1, null).intValue());
    assertEquals(30, map.getFirstOrDefault(2, 30));
    assertNull(map.getFirstOrDefault(3, 40));
  }

   @Test
   void addWithKeyValue() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.add(1, 100);
     map.add(1, 10);
     assertNull(map.getFirst(0));
     assertEquals(1, map.size());
     assertEquals(toSet(10, 100), map.get(1));
     assertTrue(map.containsKey(1));
     assertTrue(map.containsValue(toSet(10, 100)));
   }

   @Test
   void addWithEntry() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.add(new AbstractMap.SimpleEntry<>(1, 100));
     map.add(new AbstractMap.SimpleEntry<>(1, 10));
     assertNull(map.getFirst(0));
     assertEquals(1, map.size());
     assertEquals(toSet(10, 100), map.get(1));
     assertTrue(map.containsKey(1));
     assertTrue(map.containsValue(toSet(10, 100)));
   }

   @Test
   void addWithSingleValueMap() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.add(1, 20);
     Map<Integer, Integer> map2 = new HashMap<>();
     map2.put(1, 10);
     map.add(map2);
     assertNull(map.getFirst(0));
     assertEquals(1, map.size());
     assertEquals(toSet(10, 20), map.get(1));
     assertTrue(map.containsKey(1));
     assertTrue(map.containsValue(toSet(10, 20)));
   }

   @Test
   void addAllWithKeyValues() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.addAll(1, toSet(10, 20, 10, 20, 30));
     assertNull(map.getFirst(0));
     assertEquals(1, map.size());
     assertEquals(toSet(10, 20, 30), map.get(1));
   }

   @Test
   void addAllWithEntry() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.addAll(new AbstractMap.SimpleEntry<>(1, toSet(10, 20, 10, 20, 30)));
     assertNull(map.getFirst(0));
     assertEquals(1, map.size());
     assertEquals(toSet(10, 20, 30), map.get(1));
   }

   @Test
   void addAllWithSetValueMap() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     SetValueMap<Integer, Integer> map2 = new DefaultSetValueMap<>();
     map2.addAll(1, toSet(10, 20));
     map.add(1, 10);
     map.addAll(map2);
     assertNull(map.getFirst(0));
     assertEquals(1, map.size());
     assertEquals(toSet(10, 20), map.get(1));
   }

   @Test
   void setWithKeyValue() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.set(1, 1);
     map.set(1, 10);
     assertNull(map.getFirst(0));
     assertEquals(1, map.size());
     assertEquals(10, map.getFirst(1).intValue());
     assertEquals(1, map.get(1).size());
   }

   @Test
   void setWithEntry() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.set(1, 1);
     map.set(new AbstractMap.SimpleEntry<>(1, 10));
     assertNull(map.getFirst(0));
     assertEquals(1, map.size());
     assertEquals(10, map.getFirst(1).intValue());
     assertEquals(1, map.get(1).size());
   }

   @Test
   void setWithSingleValueMap() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.set(1, 1);
     Map<Integer, Integer> map2 = new HashMap<>();
     map2.put(1, 10);
     map.set(map2);
     assertNull(map.getFirst(0));
     assertEquals(1, map.size());
     assertEquals(10, map.getFirst(1).intValue());
     assertEquals(1, map.get(1).size());
   }

   @Test
   void setAllWithKeyValues() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.addAll(1, toSet(10, 20, 30));
     map.setAll(1, toSet(10, 20, 10, 20, 30));
     assertNull(map.getFirst(0));
     assertEquals(1, map.size());
     assertEquals(toSet(10, 20, 30), map.get(1));
   }

   @Test
   void setAllWithEntry() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.addAll(1, toSet(10, 20, 30));
     map.setAll(new AbstractMap.SimpleEntry<>(1, toSet(10, 20, 10, 20, 30)));
     assertNull(map.getFirst(0));
     assertEquals(1, map.size());
     assertEquals(toSet(10, 20, 30), map.get(1));
   }

   @Test
   void setAllWithSetValueMap() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.addAll(1, toSet(10, 20, 30));
     map.addAll(2, toSet(11, 12, 13));
     SetValueMap<Integer, Integer> map2 = new DefaultSetValueMap<>();
     map2.add(1, 10);
     map2.add(1, 20);
     map2.add(1, 10);
     map2.add(1, 20);
     map2.add(1, 30);
     map.setAll(map2);

     assertNull(map.getFirst(0));
     assertEquals(2, map.size());
     assertEquals(toSet(10, 20, 30), map.get(1));
     assertEquals(toSet(11, 12, 13), map.get(2));
   }

   @Test
   void toMap() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.addAll(0, toSet(1, 2, 3));
     map.addAll(1, toSet(4, 5, 6));

     Map<Integer, Set<Integer>> map2 = map.toMap();

     assertEquals(2, map2.size());
     assertEquals(toSet(1, 2, 3), map2.get(0));
     assertEquals(toSet(4, 5, 6), map2.get(1));

     assertEquals(map, map2);
     assertEquals(map2, map);
     assertEquals(map, map);
     assertEquals(map2, map2);
   }

   @Test
   void toSingleValueMap() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.addAll(0, toSet(1, 2, 3));
     map.addAll(1, toSet(4, 5, 6));

     Map<Integer, Integer> map2 = map.toSingleValueMap();
     assertEquals(2, map2.size());
     assertEquals(1, map2.get(0).intValue());
     assertEquals(4, map2.get(1).intValue());
   }

   @Test
   void testClone() {
     DefaultSetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.addAll(0, toSet(1, 2, 3));
     map.addAll(1, toSet(4, 5, 6));

     Map<Integer, Set<Integer>> map2 = map.clone();

     assertEquals(2, map2.size());
     assertEquals(toSet(1, 2, 3), map2.get(0));
     assertEquals(toSet(4, 5, 6), map2.get(1));

     assertEquals(map, map2);
     assertEquals(map2, map);
     assertEquals(map, map);
     assertEquals(map2, map2);
     assertTrue(map2 instanceof DefaultSetValueMap);
   }

   @Test
   void clear() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.addAll(1, toSet(10, 20, 30));
     map.addAll(2, toSet(11, 12, 13));
     map.clear();
     assertEquals(0, map.size());
   }

   @Test
   void keySet() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.addAll(1, toSet(10, 20, 30));
     map.addAll(2, toSet(11, 12, 13));

     List<Integer> keys = new ArrayList<>(map.keySet());
     assertEquals(2, keys.size());

     assertEquals(1, keys.get(0).intValue());
     assertEquals(2, keys.get(1).intValue());
   }

   @Test
   void values() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.addAll(1, toSet(10, 20, 30));
     map.addAll(2, toSet(11, 12, 13));

     List<Set<Integer>> values = new ArrayList<>(map.values());
     assertEquals(2, values.size());

     assertEquals(toSet(10, 20, 30), values.get(0));
     assertEquals(toSet(11, 12, 13), values.get(1));
   }

   @Test
   void entrySet() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.addAll(1, toSet(10, 20, 30));
     map.addAll(2, toSet(11, 12, 13));

     List<Map.Entry<Integer, Set<Integer>>> entries = new ArrayList<>(map.entrySet());
     assertEquals(2, entries.size());

     assertEquals(1, entries.get(0).getKey().intValue());
     assertEquals(toSet(10, 20, 30), entries.get(0).getValue());

     assertEquals(2, entries.get(1).getKey().intValue());
     assertEquals(toSet(11, 12, 13), entries.get(1).getValue());
   }

   @Test
   void testHashCode() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.addAll(1, toSet(10));
     map.addAll(2, toSet(11));
     assertEquals(20, map.hashCode());
   }

   @Test
   void testToString() {
     SetValueMap<Integer, Integer> map = new DefaultSetValueMap<>();
     map.addAll(1, toSet(10, 20));
     map.addAll(2, toSet(11, 12));
     map.addAll(3, toSet(11, 12));
     map.remove(3);
     assertEquals("{1=[20, 10], 2=[11, 12]}", map.toString());
   }

   private void assertNewEmptyMap(SetValueMap<?, ?> map) {
     assertNotNull(map);
     assertEquals(0, map.size());
     assertTrue(map.isEmpty());
   }

   @SafeVarargs
   private <T> Set<T> toSet(T ...values) {
     return new HashSet<>(Arrays.asList(values));
   }

}
