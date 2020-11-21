package com.supensour.model.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public interface SetValueMap<K, V> extends MultiValueMap<K, V, Set<V>> {

  @Override
  SetValueMap<K, V> add(K key, V value);

  @Override
  SetValueMap<K, V> add(Map.Entry<? extends K, ? extends V> entry);

  @Override
  SetValueMap<K, V> add(Map<? extends K, ? extends V> values);

  @Override
  SetValueMap<K, V> addAll(K key, Collection<? extends V> values);

  @Override
  SetValueMap<K, V> addAll(Map.Entry<? extends K, ? extends Collection<? extends V>> entry);

  @Override
  SetValueMap<K, V> addAll(Map<? extends K, ? extends Collection<? extends V>> values);

  @Override
  SetValueMap<K, V> set(K key, V value);

  @Override
  SetValueMap<K, V> set(Map.Entry<? extends K, ? extends V> entry);

  @Override
  SetValueMap<K, V> set(Map<? extends K, ? extends V> values);

  @Override
  SetValueMap<K, V> setAll(K key, Collection<? extends V> values);

  @Override
  SetValueMap<K, V> setAll(Map.Entry<? extends K, ? extends Collection<? extends V>> entry);

  @Override
  SetValueMap<K, V> setAll(Map<? extends K, ? extends Collection<? extends V>> values);

}
