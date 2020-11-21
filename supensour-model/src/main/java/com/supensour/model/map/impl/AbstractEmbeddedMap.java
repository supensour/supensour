package com.supensour.model.map.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public abstract class AbstractEmbeddedMap<K, V> implements Map<K, V> {

  protected Map<K, V> innerMap;

  @Override
  public int size() {
    return innerMap.size();
  }

  @Override
  public boolean isEmpty() {
    return innerMap.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return innerMap.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return innerMap.containsValue(value);
  }

  @Override
  public V get(Object key) {
    return innerMap.get(key);
  }

  @Override
  public V put(K key, V value) {
    return innerMap.put(key, value);
  }

  @Override
  public V remove(Object key) {
    return innerMap.remove(key);
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    innerMap.putAll(m);
  }

  @Override
  public void clear() {
    innerMap.clear();
  }

  @Override
  public Set<K> keySet() {
    return innerMap.keySet();
  }

  @Override
  public Collection<V> values() {
    return innerMap.values();
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return innerMap.entrySet();
  }

  @Override
  public int hashCode() {
    return innerMap.hashCode();
  }

  @Override
  @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
  public boolean equals(Object obj) {
    return innerMap.equals(obj);
  }

  @Override
  public String toString() {
    return innerMap.toString();
  }

}
