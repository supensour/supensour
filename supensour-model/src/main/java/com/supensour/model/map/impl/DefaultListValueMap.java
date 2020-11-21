package com.supensour.model.map.impl;

import com.supensour.model.map.ListValueMap;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Suprayan Yapura
 * @since 1.0.0
 */
public class DefaultListValueMap<K, V> extends AbstractMultiValueMap<K, V, List<V>> implements ListValueMap<K, V> {

  private static final long serialVersionUID = -8677431529553338132L;

  public DefaultListValueMap(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  public DefaultListValueMap(int initialCapacity) {
    super(initialCapacity);
  }

  public DefaultListValueMap(Map<? extends K, ? extends Collection<V>> m) {
    super(m);
  }

  public DefaultListValueMap(K key, V value) {
    super(1);
    add(key, value);
  }

  public DefaultListValueMap(K key, Collection<? extends V> values)  {
    super(1);
    addAll(key, values);
  }

  public DefaultListValueMap() {
    super();
  }

  @Override
  protected List<V> newCollection() {
    return new LinkedList<>();
  }

  @Override
  public DefaultListValueMap<K, V> add(K key, V value) {
    return (DefaultListValueMap<K, V>) super.add(key, value);
  }

  @Override
  public DefaultListValueMap<K, V> add(Entry<? extends K, ? extends V> entry) {
    return (DefaultListValueMap<K, V>) super.add(entry);
  }

  @Override
  public DefaultListValueMap<K, V> add(Map<? extends K, ? extends V> values) {
    return (DefaultListValueMap<K, V>) super.add(values);
  }

  @Override
  public DefaultListValueMap<K, V> addAll(K key, Collection<? extends V> values) {
    return (DefaultListValueMap<K, V>) super.addAll(key, values);
  }

  @Override
  public DefaultListValueMap<K, V> addAll(Entry<? extends K, ? extends Collection<? extends V>> entry) {
    return (DefaultListValueMap<K, V>) super.addAll(entry);
  }

  @Override
  public DefaultListValueMap<K, V> addAll(Map<? extends K, ? extends Collection<? extends V>> values) {
    return (DefaultListValueMap<K, V>) super.addAll(values);
  }

  @Override
  public DefaultListValueMap<K, V> set(K key, V value) {
    return (DefaultListValueMap<K, V>) super.set(key, value);
  }

  @Override
  public DefaultListValueMap<K, V> set(Entry<? extends K, ? extends V> entry) {
    return (DefaultListValueMap<K, V>) super.set(entry);
  }

  @Override
  public DefaultListValueMap<K, V> set(Map<? extends K, ? extends V> values) {
    return (DefaultListValueMap<K, V>) super.set(values);
  }

  @Override
  public DefaultListValueMap<K, V> setAll(K key, Collection<? extends V> values) {
    return (DefaultListValueMap<K, V>) super.setAll(key, values);
  }

  @Override
  public DefaultListValueMap<K, V> setAll(Entry<? extends K, ? extends Collection<? extends V>> entry) {
    return (DefaultListValueMap<K, V>) super.setAll(entry);
  }

  @Override
  public DefaultListValueMap<K, V> setAll(Map<? extends K, ? extends Collection<? extends V>> values) {
    return (DefaultListValueMap<K, V>) super.setAll(values);
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public ListValueMap<K, V> clone() {
    return new DefaultListValueMap<>(this);
  }

}
