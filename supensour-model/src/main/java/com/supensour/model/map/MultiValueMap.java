package com.supensour.model.map;

import java.util.Collection;
import java.util.Map;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public interface MultiValueMap<K, V, C extends Collection<V>> extends Map<K, C> {

  V getFirst(K key);

  V getFirstOrDefault(K key, V defaultValue);

  MultiValueMap<K, V, C> add(K key, V value);

  MultiValueMap<K, V, C> add(Entry<? extends K, ? extends V> entry);

  MultiValueMap<K, V, C> add(Map<? extends K, ? extends V> values);

  MultiValueMap<K, V, C> addAll(K key, Collection<? extends V> values);

  MultiValueMap<K, V, C> addAll(Entry<? extends K, ? extends Collection<? extends V>> entry);

  MultiValueMap<K, V, C> addAll(Map<? extends K, ? extends Collection<? extends V>> values);

  MultiValueMap<K, V, C> set(K key, V value);

  MultiValueMap<K, V, C> set(Entry<? extends K, ? extends V> entry);

  MultiValueMap<K, V, C> set(Map<? extends K, ? extends V> values);

  MultiValueMap<K, V, C> setAll(K key, Collection<? extends V> values);

  MultiValueMap<K, V, C> setAll(Entry<? extends K, ? extends Collection<? extends V>> entry);

  MultiValueMap<K, V, C> setAll(Map<? extends K, ? extends Collection<? extends V>> values);

  Map<K, C> toMap();

  Map<K, V> toSingleValueMap();

}
