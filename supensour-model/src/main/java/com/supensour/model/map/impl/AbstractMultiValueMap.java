package com.supensour.model.map.impl;

import com.supensour.model.map.MultiValueMap;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public abstract class AbstractMultiValueMap<K, V, C extends Collection<V>> extends AbstractEmbeddedMap<K, C>
    implements MultiValueMap<K, V, C>, Serializable, Cloneable {

  private static final long serialVersionUID = 9147767764021211521L;

  protected AbstractMultiValueMap(int initialCapacity, float loadFactor) {
    this.innerMap = new LinkedHashMap<>(initialCapacity, loadFactor);
  }

  protected AbstractMultiValueMap(int initialCapacity) {
    this.innerMap = new LinkedHashMap<>(initialCapacity);
  }

  protected AbstractMultiValueMap(Map<? extends K, ? extends Collection<V>> m) {
    this.innerMap = new LinkedHashMap<>(m.size());
    this.addAll(m);
  }

  protected AbstractMultiValueMap() {
    this.innerMap = new LinkedHashMap<>();
  }

  abstract protected C newCollection();

  @Override
  public V getFirst(K key) {
    return Optional.ofNullable(get(key))
        .map(Collection::iterator)
        .filter(Iterator::hasNext)
        .map(Iterator::next)
        .orElse(null);
  }

  @Override
  public V getFirstOrDefault(K key, V defaultValue) {
    return Optional.ofNullable(get(key))
        .map(Collection::iterator)
        .filter(Iterator::hasNext)
        .or(() -> Optional.of(Collections.singleton(defaultValue).iterator()))
        .map(Iterator::next)
        .orElse(null);
  }

  @Override
  public AbstractMultiValueMap<K, V, C> add(K key, V value) {
    computeIfAbsent(key, k -> newCollection()).add(value);
    return this;
  }

  @Override
  public AbstractMultiValueMap<K, V, C> add(Entry<? extends K, ? extends V> entry) {
    add(entry.getKey(), entry.getValue());
    return this;
  }

  @Override
  public AbstractMultiValueMap<K, V, C> add(Map<? extends K, ? extends V> values) {
    values.forEach(this::add);
    return this;
  }

  @Override
  public AbstractMultiValueMap<K, V, C> addAll(K key, Collection<? extends V> values) {
    computeIfAbsent(key, k -> newCollection()).addAll(values);
    return this;
  }

  @Override
  public AbstractMultiValueMap<K, V, C> addAll(Entry<? extends K, ? extends Collection<? extends V>> entry) {
    addAll(entry.getKey(), entry.getValue());
    return this;
  }

  @Override
  public AbstractMultiValueMap<K, V, C> addAll(Map<? extends K, ? extends Collection<? extends V>> values) {
    values.forEach(this::addAll);
    return this;
  }

  @Override
  public AbstractMultiValueMap<K, V, C> set(K key, V value) {
    compute(key, (k,  old) -> newCollection()).add(value);
    return this;
  }

  @Override
  public AbstractMultiValueMap<K, V, C> set(Entry<? extends K, ? extends V> entry) {
    set(entry.getKey(), entry.getValue());
    return this;
  }

  @Override
  public AbstractMultiValueMap<K, V, C> set(Map<? extends K, ? extends V> values) {
    values.forEach(this::set);
    return this;
  }

  @Override
  public AbstractMultiValueMap<K, V, C> setAll(K key, Collection<? extends V> values) {
    compute(key, (k, old) -> newCollection()).addAll(values);
    return this;
  }

  @Override
  public AbstractMultiValueMap<K, V, C> setAll(Entry<? extends K, ? extends Collection<? extends V>> entry) {
    setAll(entry.getKey(), entry.getValue());
    return this;
  }

  @Override
  public AbstractMultiValueMap<K, V, C> setAll(Map<? extends K, ? extends Collection<? extends V>> values) {
    values.forEach(this::setAll);
    return this;
  }

  @Override
  public Map<K, C> toMap() {
    Map<K, C> map = new LinkedHashMap<>();
    for (Entry<K, C> entry: entrySet()) {
      C values = Optional.ofNullable(entry.getValue())
          .map(this::cloneValues)
          .orElse(null);
      map.put(entry.getKey(), values);
    }
    return map;
  }

  @Override
  public Map<K, V> toSingleValueMap() {
    Map<K, V> map = new LinkedHashMap<>();
    for (Entry<K, C> entry: entrySet()) {
      V value = Optional.ofNullable(entry.getValue())
          .map(Collection::iterator)
          .filter(Iterator::hasNext)
          .map(Iterator::next)
          .orElse(null);
      map.put(entry.getKey(), value);
    }
    return map;
  }

  private C cloneValues(C values) {
    C newValues = newCollection();
    newValues.addAll(values);
    return newValues;
  }

}
