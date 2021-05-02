package com.supensour.core.utils;

import com.supensour.model.group.Pair;
import com.supensour.model.map.MultiValueMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionUtils {

  private static final String NULL_COLLECTION_SUPPLIER = "collection supplier must not be null";
  private static final String NULL_MAP_SUPPLIER = "map supplier must not be null";
  private static final String NULL_KEY = "Key must not be null";
  private static final String NULL_KEY_VALUE_PAIR = "key-value pair must not be null";
  private static final String NULL_ENTRY = "entry must not be null";
  private static final String NULL_OTHER_MAP = "other map must not be null";

  private static final Set<?> EMPTY_UNMODIFIABLE_SET = Collections.unmodifiableSet(new HashSet<>());
  private static final List<?> EMPTY_UNMODIFIABLE_LIST = Collections.unmodifiableList(new ArrayList<>());
  private static final Map<?, ?> EMPTY_UNMODIFIABLE_MAP = Collections.unmodifiableMap(new HashMap<>());

  /**
   * To check whether given map is not empty
   *
   * @param map   the map to be checked
   * @return true only if the map is not empty
   */
  public static boolean isNotEmpty(Map<?, ?> map) {
    return Optional.ofNullable(map)
        .filter(m -> !m.isEmpty())
        .isPresent();
  }

  /**
   * To check whether given multi value map is not empty
   *
   * @param multiValueMap   the multi value map to be checked
   * @return true only if the multi value map is not empty
   */
  public static boolean isNotEmpty(MultiValueMap<?, ?, ?> multiValueMap) {
    return Optional.ofNullable(multiValueMap)
        .filter(m -> !m.isEmpty())
        .isPresent();
  }

  /**
   * To check whether given collection is not empty
   *
   * @param collection  the collection to be checked
   * @return true only if the collection is not empty
   */
  public static boolean isNotEmpty(Collection<?> collection) {
    return Optional.ofNullable(collection)
        .filter(c -> !c.isEmpty())
        .isPresent();
  }

  /**
   * To check whether given iterable is not empty
   *
   * @param iterable  the iterable to be checked
   * @return true only if the iterable is not empty
   */
  public static boolean isNotEmpty(Iterable<?> iterable) {
    return Optional.ofNullable(iterable)
        .filter(i -> i.iterator().hasNext())
        .isPresent();
  }

  /**
   * To check whether given array is not empty
   *
   * @param array  the array to be checked
   * @return true only if the array is not empty
   */
  public static boolean isNotEmpty(Object[] array) {
    return Optional.ofNullable(array)
        .filter(a -> a.length > 0)
        .isPresent();
  }

  /**
   * To check whether given map is null or empty
   *
   * @param map   the map to be checked
   * @return true only if the map is null or empty
   */
  public static boolean isEmpty(Map<?, ?> map) {
    return !isNotEmpty(map);
  }

  /**
   * To check whether given multi value map is null or empty
   *
   * @param multiValueMap   the multi value map to be checked
   * @return true only if the multi value map is null or empty
   */
  public static boolean isEmpty(MultiValueMap<?, ?, ?> multiValueMap) {
    return !isNotEmpty(multiValueMap);
  }

  /**
   * To check whether given collection is null or empty
   *
   * @param collection  the collection to be checked
   * @return true only if the collection is null or empty
   */
  public static boolean isEmpty(Collection<?> collection) {
    return !isNotEmpty(collection);
  }

  /**
   * To check whether given iterable is null or empty
   *
   * @param iterable  the iterable to be checked
   * @return true only if the iterable is null or empty
   */
  public static boolean isEmpty(Iterable<?> iterable) {
    return !isNotEmpty(iterable);
  }

  /**
   * To check whether given array is null or empty
   *
   * @param array  the array to be checked
   * @return true only if the array is null or empty
   */
  public static boolean isEmpty(Object[] array) {
    return !isNotEmpty(array);
  }

  /**
   * Collect values to {@link List}.
   *
   * @param values  values to be collected to list
   *
   * @param <T>     type
   *
   * @return a list contains collected values
   */
  @SafeVarargs
  public static <T> List<T> toList(T... values) {
    return new ArrayList<>(Arrays.asList(values));
  }

  /**
   * Collect values to {@link Set}.
   *
   * @param values  values to be collected to set
   *
   * @param <T>     type
   *
   * @return a set contains unique collected values
   */
  @SafeVarargs
  public static <T> Set<T> toSet(T... values) {
    return new HashSet<>(Arrays.asList(values));
  }

  /**
   * Add value to map.
   *
   * This is an overloaded method that receive {@link Pair}.
   * The key should be stored as {@link Pair} first.
   * The value should be stored as {@link Pair} second.
   *
   * @param map     the map to be added to
   * @param kvPair  a pair contains of key and value to be added
   *
   * @param <K>     key
   * @param <V>     value
   *
   * @throws NullPointerException if kvPair or the key is null
   * @return map
   */
  public static <K, V> Map<K, V> addToMap(Map<K, V> map, Pair<K, V> kvPair) {
    Objects.requireNonNull(kvPair, NULL_KEY_VALUE_PAIR);
    return addToMap(map, kvPair.getFirst(), kvPair.getSecond());
  }

  /**
   * Add value to map.
   *
   * This is an overloaded method that receive {@link Entry}.
   *
   * @param map     the map to be added to
   * @param entry   a map entry
   *
   * @param <K>     key
   * @param <V>     value
   *
   * @throws NullPointerException if entry or the key is null
   * @return map
   */
  public static <K, V> Map<K, V> addToMap(Map<K, V> map, Entry<K, V> entry) {
    Objects.requireNonNull(entry, NULL_ENTRY);
    return addToMap(map, entry.getKey(), entry.getValue());
  }

  /**
   * Add value to map.
   *
   * @param map     the map to be added to
   * @param key     the key where the value to be added on
   * @param value   the value to be added
   *
   * @param <K>     key
   * @param <V>     value
   *
   * @throws NullPointerException if the key is null
   * @return map
   */
  public static <K, V> Map<K, V> addToMap(Map<K, V> map, K key, V value) {
    Objects.requireNonNull(key, NULL_KEY);
    map = Optional.ofNullable(map).orElseGet(HashMap::new);
    map.put(key, value);
    return map;
  }

  /**
   * Add value to map.
   *
   * This is an overloaded method that receive {@link Pair}.
   * The key should be stored as {@link Pair} first.
   * The value should be stored as {@link Pair} second.
   *
   * @param mapSupplier   supplier of the map to be added to
   * @param kvPair        a pair contains of key and value to be added
   *
   * @param <K>           key
   * @param <V>           value
   *
   * @throws NullPointerException if mapSupplier, kvPair or the key is null
   * @return map
   */
  public static <K, V> Map<K, V> addToMap(Supplier<Map<K, V>> mapSupplier, Pair<K, V> kvPair) {
    Objects.requireNonNull(mapSupplier, NULL_MAP_SUPPLIER);
    return addToMap(mapSupplier.get(), kvPair);
  }

  /**
   * Add value to map.
   *
   * This is an overloaded method that receive {@link Entry}.
   *
   * @param mapSupplier   supplier of the map to be added to
   * @param entry         a map entry
   *
   * @param <K>           key
   * @param <V>           value
   *
   * @throws NullPointerException if mapSupplier, entry or the key is null
   * @return map
   */
  public static <K, V> Map<K, V> addToMap(Supplier<Map<K, V>> mapSupplier, Entry<K, V> entry) {
    Objects.requireNonNull(mapSupplier, NULL_MAP_SUPPLIER);
    return addToMap(mapSupplier.get(), entry);
  }

  /**
   * Add value to map.
   *
   * @param mapSupplier   supplier of the map to be added to
   * @param key           the key where the value to be added on
   * @param value         the value to be added
   *
   * @param <K>           key
   * @param <V>           value
   *
   * @throws NullPointerException if mapSupplier or the key is null
   * @return map
   */
  public static <K, V> Map<K, V> addToMap(Supplier<Map<K, V>> mapSupplier, K key, V value) {
    Objects.requireNonNull(mapSupplier, NULL_MAP_SUPPLIER);
    return addToMap(mapSupplier.get(), key, value);
  }

  public static <K, V> Map<K, List<V>> addToMultiValueMap(Map<K, List<V>> map, Pair<K, V> kvPair) {
    return addToMultiValueMap(map, kvPair, ArrayList::new);
  }

  public static <K, V> Map<K, List<V>> addToMultiValueMap(Map<K, List<V>> map, Entry<K, V> entry) {
    return addToMultiValueMap(map, entry, ArrayList::new);
  }

  public static <K, V> Map<K, List<V>> addToMultiValueMap(Map<K, List<V>> map, Map<K, V> other) {
    return addToMultiValueMap(map, other, ArrayList::new);
  }

  public static <K, V> Map<K, List<V>> addToMultiValueMap(Map<K, List<V>> map, K key, V value) {
    return addToMultiValueMap(map, key, value, ArrayList::new);
  }

  public static <K, V> Map<K, List<V>> addToMultiValueMap(Supplier<Map<K, List<V>>> mapSupplier, Pair<K, V> kvPair) {
    return addToMultiValueMap(mapSupplier, kvPair, ArrayList::new);
  }

  public static <K, V> Map<K, List<V>> addToMultiValueMap(Supplier<Map<K, List<V>>> mapSupplier, Entry<K, V> entry) {
    return addToMultiValueMap(mapSupplier, entry, ArrayList::new);
  }

  public static <K, V> Map<K, List<V>> addToMultiValueMap(Supplier<Map<K, List<V>>> mapSupplier, Map<K, V> other) {
    return addToMultiValueMap(mapSupplier, other, ArrayList::new);
  }

  public static <K, V> Map<K, List<V>> addToMultiValueMap(Supplier<Map<K, List<V>>> mapSupplier, K key, V value) {
    return addToMultiValueMap(mapSupplier, key, value, ArrayList::new);
  }

  public static <K, V> Map<K, List<V>> addAllToMultiValueMap(Map<K, List<V>> map, Pair<K, ? extends Collection<V>> kvPair) {
    return addAllToMultiValueMap(map, kvPair, ArrayList::new);
  }

  public static <K, V> Map<K, List<V>> addAllToMultiValueMap(Map<K, List<V>> map, Entry<K, ? extends Collection<V>> entry) {
    return addAllToMultiValueMap(map, entry, ArrayList::new);
  }

  public static <K, V> Map<K, List<V>> addAllToMultiValueMap(Map<K, List<V>> map, Map<K, ? extends Collection<V>> other) {
    return addAllToMultiValueMap(map, other, ArrayList::new);
  }

  public static <K, V> Map<K, List<V>> addAllToMultiValueMap(Map<K, List<V>> map, K key, Collection<V> value) {
    return addAllToMultiValueMap(map, key, value, ArrayList::new);
  }

  public static <K, V> Map<K, List<V>> addAllToMultiValueMap(Supplier<Map<K, List<V>>> mapSupplier, Pair<K, ? extends Collection<V>> kvPair) {
    return addAllToMultiValueMap(mapSupplier, kvPair, ArrayList::new);
  }

  public static <K, V> Map<K, List<V>> addAllToMultiValueMap(Supplier<Map<K, List<V>>> mapSupplier, Entry<K, ? extends Collection<V>> entry) {
    return addAllToMultiValueMap(mapSupplier, entry, ArrayList::new);
  }

  public static <K, V> Map<K, List<V>> addAllToMultiValueMap(Supplier<Map<K, List<V>>> mapSupplier, Map<K, ? extends Collection<V>> other) {
    return addAllToMultiValueMap(mapSupplier, other, ArrayList::new);
  }

  public static <K, V> Map<K, List<V>> addAllToMultiValueMap(Supplier<Map<K, List<V>>> mapSupplier, K key, Collection<V> value) {
    return addAllToMultiValueMap(mapSupplier, key, value, ArrayList::new);
  }

  //

  public static <K, V, C extends Collection<V>> Map<K, C> addToMultiValueMap(Map<K, C> map, Pair<K, V> kvPair, Supplier<C> collectionSupplier) {
    Objects.requireNonNull(kvPair, NULL_KEY_VALUE_PAIR);
    return addToMultiValueMap(map, kvPair.getFirst(), kvPair.getSecond(), collectionSupplier);
  }

  public static <K, V, C extends Collection<V>> Map<K, C> addToMultiValueMap(Map<K, C> map, Entry<K, V> entry, Supplier<C> collectionSupplier) {
    Objects.requireNonNull(entry, NULL_ENTRY);
    return addToMultiValueMap(map, entry.getKey(), entry.getValue(), collectionSupplier);
  }

  public static <K, V, C extends Collection<V>> Map<K, C> addToMultiValueMap(Map<K, C> map, Map<K, V> other, Supplier<C> collectionSupplier) {
    Objects.requireNonNull(other, NULL_OTHER_MAP);
    for (Entry<K, V> entry: other.entrySet()) {
      map = addToMultiValueMap(map, entry.getKey(), entry.getValue(), collectionSupplier);
    }
    return map;
  }

  public static <K, V, C extends Collection<V>> Map<K, C> addToMultiValueMap(Map<K, C> map, K key, V value, Supplier<C> collectionSupplier) {
    Objects.requireNonNull(collectionSupplier, NULL_COLLECTION_SUPPLIER);
    Objects.requireNonNull(key, NULL_KEY);
    map = Optional.ofNullable(map).orElseGet(HashMap::new);
    var list = map.computeIfAbsent(key, k -> collectionSupplier.get());
    list.add(value);
    return map;
  }

  public static <K, V, C extends Collection<V>> Map<K, C> addToMultiValueMap(Supplier<Map<K, C>> mapSupplier, Pair<K, V> kvPair, Supplier<C> collectionSupplier) {
    Objects.requireNonNull(mapSupplier, NULL_MAP_SUPPLIER);
    return addToMultiValueMap(mapSupplier.get(), kvPair, collectionSupplier);
  }

  public static <K, V, C extends Collection<V>> Map<K, C> addToMultiValueMap(Supplier<Map<K, C>> mapSupplier, Entry<K, V> entry, Supplier<C> collectionSupplier) {
    Objects.requireNonNull(mapSupplier, NULL_MAP_SUPPLIER);
    return addToMultiValueMap(mapSupplier.get(), entry, collectionSupplier);
  }

  public static <K, V, C extends Collection<V>> Map<K, C> addToMultiValueMap(Supplier<Map<K, C>> mapSupplier, Map<K, V> other, Supplier<C> collectionSupplier) {
    Objects.requireNonNull(mapSupplier, NULL_MAP_SUPPLIER);
    return addToMultiValueMap(mapSupplier.get(), other, collectionSupplier);
  }

  public static <K, V, C extends Collection<V>> Map<K, C> addToMultiValueMap(Supplier<Map<K, C>> mapSupplier, K key, V value, Supplier<C> collectionSupplier) {
    Objects.requireNonNull(mapSupplier, NULL_MAP_SUPPLIER);
    return addToMultiValueMap(mapSupplier.get(), key, value, collectionSupplier);
  }

  public static <K, V, C extends Collection<V>> Map<K, C> addAllToMultiValueMap(Map<K, C> map, Pair<K, ? extends Collection<V>> kvPair, Supplier<C> collectionSupplier) {
    Objects.requireNonNull(kvPair, NULL_KEY_VALUE_PAIR);
    return addAllToMultiValueMap(map, kvPair.getFirst(), kvPair.getSecond(), collectionSupplier);
  }

  public static <K, V, C extends Collection<V>> Map<K, C> addAllToMultiValueMap(Map<K, C> map, Entry<K, ? extends Collection<V>> entry, Supplier<C> collectionSupplier) {
    Objects.requireNonNull(entry, NULL_ENTRY);
    return addAllToMultiValueMap(map, entry.getKey(), entry.getValue(), collectionSupplier);
  }

  public static <K, V, C extends Collection<V>> Map<K, C> addAllToMultiValueMap(Map<K, C> map, Map<K, ? extends Collection<V>> other, Supplier<C> collectionSupplier) {
    Objects.requireNonNull(other, NULL_OTHER_MAP);
    for (Entry<K, ? extends Collection<V>> entry: other.entrySet()) {
      map = addAllToMultiValueMap(map, entry.getKey(), entry.getValue(), collectionSupplier);
    }
    return map;
  }

  public static <K, V, C extends Collection<V>> Map<K, C> addAllToMultiValueMap(Map<K, C> map, K key, Collection<V> value, Supplier<C> collectionSupplier) {
    Objects.requireNonNull(key, NULL_KEY);
    map = Optional.ofNullable(map).orElseGet(HashMap::new);
    var list = map.computeIfAbsent(key, k -> collectionSupplier.get());
    list.addAll(value);
    return map;
  }

  public static <K, V, C extends Collection<V>> Map<K, C> addAllToMultiValueMap(Supplier<Map<K, C>> mapSupplier, Pair<K, ? extends Collection<V>> kvPair, Supplier<C> collectionSupplier) {
    Objects.requireNonNull(mapSupplier, NULL_MAP_SUPPLIER);
    return addAllToMultiValueMap(mapSupplier.get(), kvPair, collectionSupplier);
  }

  public static <K, V, C extends Collection<V>> Map<K, C> addAllToMultiValueMap(Supplier<Map<K, C>> mapSupplier, Entry<K, ? extends Collection<V>> entry, Supplier<C> collectionSupplier) {
    Objects.requireNonNull(mapSupplier, NULL_MAP_SUPPLIER);
    return addAllToMultiValueMap(mapSupplier.get(), entry, collectionSupplier);
  }

  public static <K, V, C extends Collection<V>> Map<K, C> addAllToMultiValueMap(Supplier<Map<K, C>> mapSupplier, Map<K, ? extends Collection<V>> other, Supplier<C> collectionSupplier) {
    Objects.requireNonNull(mapSupplier, NULL_MAP_SUPPLIER);
    return addAllToMultiValueMap(mapSupplier.get(), other, collectionSupplier);
  }

  public static <K, V, C extends Collection<V>> Map<K, C> addAllToMultiValueMap(Supplier<Map<K, C>> mapSupplier, K key, Collection<V> value, Supplier<C> collectionSupplier) {
    Objects.requireNonNull(mapSupplier, NULL_MAP_SUPPLIER);
    return addAllToMultiValueMap(mapSupplier.get(), key, value, collectionSupplier);
  }

  /**
   * Join all collection sources to the target
   * Make sure that the target is not null or the result will be null.
   *
   * @param target    the target variable where the sources to be appended to
   * @param sources   list of collections to be appended to the target
   *
   * @param <T>       type
   * @param <C>       collection
   *
   * @return the target
   */
  @SafeVarargs
  public static <T, C extends Collection<T>> C join(C target, Collection<T>... sources) {
    if (target != null) {
      Arrays.stream(sources)
          .filter(Objects::nonNull)
          .forEach(target::addAll);
    }
    return target;
  }

  /**
   * Join all collection sources to the target
   *
   * @param targetSupplier  the target supplier where the sources to be appended to
   * @param sources         list of collections to be appended to the target
   *
   * @param <T>             type
   * @param <C>             collection
   *
   * @throws NullPointerException if targetSupplier is null
   * @return the target
   */
  @SafeVarargs
  public static <T, C extends Collection<T>> C join(Supplier<C> targetSupplier, Collection<T>... sources) {
    Objects.requireNonNull(targetSupplier, "Target supplier must not be null");
    return join(targetSupplier.get(), sources);
  }

  /**
   * Join all map sources to the target
   * Make sure that the target is not null or the result will be null.
   *
   * @param target    the target variable where the sources to be appended to
   * @param sources   list of maps to be appended to the target
   *
   * @param <K>       key
   * @param <V>       value
   * @param <M>       map
   *
   * @return the target
   */
  @SafeVarargs
  public static <K, V, M extends Map<K, V>> M joinMaps(M target, Map<K, V>... sources) {
    if (target != null) {
      Arrays.stream(sources)
          .filter(Objects::nonNull)
          .forEach(target::putAll);
    }
    return target;
  }

  /**
   * Join all map sources to the target
   *
   * @param targetSupplier  the target supplier where the sources to be appended to
   * @param sources         list of maps to be appended to the target
   *
   * @param <K>             key
   * @param <V>             value
   * @param <M>             map
   *
   * @throws NullPointerException if targetSupplier is null
   * @return the target
   */
  @SafeVarargs
  public static <K, V, M extends Map<K, V>> M joinMaps(Supplier<M> targetSupplier, Map<K, V>... sources) {
    Objects.requireNonNull(targetSupplier, "Target supplier must not be null");
    return joinMaps(targetSupplier.get(), sources);
  }

  public static <T> boolean isUnmodifiable(Set<T> set) {
    return EMPTY_UNMODIFIABLE_SET.getClass().isInstance(set);
  }

  public static <T> boolean isUnmodifiable(List<T> list) {
    return EMPTY_UNMODIFIABLE_LIST.getClass().isInstance(list);
  }

  public static <K, V> boolean isUnmodifiable(Map<K, V> map) {
    return EMPTY_UNMODIFIABLE_MAP.getClass().isInstance(map);
  }

  public static <T> Set<T> unmodifiable(Set<T> set) {
    return Collections.unmodifiableSet(set);
  }

  public static <T> List<T> unmodifiable(List<T> list) {
    return Collections.unmodifiableList(list);
  }

  public static <K, V> Map<K, V> unmodifiable(Map<K, V> map) {
    return Collections.unmodifiableMap(map);
  }

}
