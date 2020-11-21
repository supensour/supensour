package com.supensour.model.map.impl;

import com.supensour.model.map.SetValueMap;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public class DefaultSetValueMap<K, V> extends AbstractMultiValueMap<K, V, Set<V>> implements SetValueMap<K, V> {

  private static final long serialVersionUID = -2699430733197775471L;

  public DefaultSetValueMap(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  public DefaultSetValueMap(int initialCapacity) {
    super(initialCapacity);
  }

  public DefaultSetValueMap(Map<? extends K, ? extends Collection<V>> m) {
    super(m);
  }

  public DefaultSetValueMap(K key, V value) {
    super(1);
    add(key, value);
  }

  public DefaultSetValueMap(K key, Collection<? extends V> values)  {
    super(1);
    addAll(key, values);
  }

  public DefaultSetValueMap() {
    super();
  }

  @Override
  protected Set<V> newCollection() {
    return new LinkedHashSet<>();
  }

  @Override
  public DefaultSetValueMap<K, V> add(K key, V value) {
    return (DefaultSetValueMap<K, V>) super.add(key, value);
  }

  @Override
  public DefaultSetValueMap<K, V> add(Entry<? extends K, ? extends V> entry) {
    return (DefaultSetValueMap<K, V>) super.add(entry);
  }

  @Override
  public DefaultSetValueMap<K, V> add(Map<? extends K, ? extends V> values) {
    return (DefaultSetValueMap<K, V>) super.add(values);
  }

  @Override
  public DefaultSetValueMap<K, V> addAll(K key, Collection<? extends V> values) {
    return (DefaultSetValueMap<K, V>) super.addAll(key, values);
  }

  @Override
  public DefaultSetValueMap<K, V> addAll(Entry<? extends K, ? extends Collection<? extends V>> entry) {
    return (DefaultSetValueMap<K, V>) super.addAll(entry);
  }

  @Override
  public DefaultSetValueMap<K, V> addAll(Map<? extends K, ? extends Collection<? extends V>> values) {
    return (DefaultSetValueMap<K, V>) super.addAll(values);
  }

  @Override
  public DefaultSetValueMap<K, V> set(K key, V value) {
    return (DefaultSetValueMap<K, V>) super.set(key, value);
  }

  @Override
  public DefaultSetValueMap<K, V> set(Entry<? extends K, ? extends V> entry) {
    return (DefaultSetValueMap<K, V>) super.set(entry);
  }

  @Override
  public DefaultSetValueMap<K, V> set(Map<? extends K, ? extends V> values) {
    return (DefaultSetValueMap<K, V>) super.set(values);
  }

  @Override
  public DefaultSetValueMap<K, V> setAll(K key, Collection<? extends V> values) {
    return (DefaultSetValueMap<K, V>) super.setAll(key, values);
  }

  @Override
  public DefaultSetValueMap<K, V> setAll(Entry<? extends K, ? extends Collection<? extends V>> entry) {
    return (DefaultSetValueMap<K, V>) super.setAll(entry);
  }

  @Override
  public DefaultSetValueMap<K, V> setAll(Map<? extends K, ? extends Collection<? extends V>> values) {
    return (DefaultSetValueMap<K, V>) super.setAll(values);
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public SetValueMap<K, V> clone() {
    return new DefaultSetValueMap<>(this);
  }

}
