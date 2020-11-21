package com.supensour.model.map;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public interface ListValueMap<K, V> extends MultiValueMap<K, V, List<V>> {

  @Override
  ListValueMap<K, V> add(K key, V value);

  @Override
  ListValueMap<K, V> add(Map.Entry<? extends K, ? extends V> entry);

  @Override
  ListValueMap<K, V> add(Map<? extends K, ? extends V> values);

  @Override
  ListValueMap<K, V> addAll(K key, Collection<? extends V> values);

  @Override
  ListValueMap<K, V> addAll(Map.Entry<? extends K, ? extends Collection<? extends V>> entry);

  @Override
  ListValueMap<K, V> addAll(Map<? extends K, ? extends Collection<? extends V>> values);

  @Override
  ListValueMap<K, V> set(K key, V value);

  @Override
  ListValueMap<K, V> set(Map.Entry<? extends K, ? extends V> entry);

  @Override
  ListValueMap<K, V> set(Map<? extends K, ? extends V> values);

  @Override
  ListValueMap<K, V> setAll(K key, Collection<? extends V> values);

  @Override
  ListValueMap<K, V> setAll(Map.Entry<? extends K, ? extends Collection<? extends V>> entry);

  @Override
  ListValueMap<K, V> setAll(Map<? extends K, ? extends Collection<? extends V>> values);

}
