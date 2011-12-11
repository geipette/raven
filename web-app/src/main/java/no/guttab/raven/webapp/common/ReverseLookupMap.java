package no.guttab.raven.webapp.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ReverseLookupMap<K, V> implements Map<K, V> {
   private Map<K, V> map = new HashMap<K, V>();
   private Map<V, K> reverseMap = new HashMap<V, K>();

   public K getByValue(V value) {
      return reverseMap.get(value);
   }

   @Override
   public int size() {
      return map.size();
   }

   @Override
   public boolean isEmpty() {
      return map.isEmpty();
   }

   @Override
   public boolean containsKey(Object key) {
      return map.containsKey(key);
   }

   @SuppressWarnings({"SuspiciousMethodCalls"})
   @Override
   public boolean containsValue(Object value) {
      return reverseMap.containsKey(value);
   }

   @Override
   public V get(Object key) {
      return map.get(key);
   }

   public V put(K key, V value) {
      reverseMap.put(value, key);
      return map.put(key, value);
   }

   @Override
   public V remove(Object key) {
      V previousValue = map.remove(key);
      reverseMap.remove(previousValue);
      return previousValue;
   }

   public void putAll(Map<? extends K, ? extends V> m) {
      for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
         reverseMap.put(entry.getValue(), entry.getKey());
      }
      map.putAll(m);
   }

   @Override
   public void clear() {
      reverseMap.clear();
      map.clear();
   }

   @Override
   public Set<K> keySet() {
      return map.keySet();
   }

   @Override
   public Collection<V> values() {
      return map.values();
   }

   @Override
   public Set<Entry<K, V>> entrySet() {
      return map.entrySet();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      ReverseLookupMap that = (ReverseLookupMap) o;

      return map.equals(that.map);

   }

   @Override
   public int hashCode() {
      return map.hashCode();
   }
}
