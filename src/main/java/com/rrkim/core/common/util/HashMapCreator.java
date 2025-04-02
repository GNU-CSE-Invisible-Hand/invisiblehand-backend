package com.rrkim.core.common.util;

import java.util.HashMap;
import java.util.Map;

public class HashMapCreator<K, V> {

    private Map<K, V> map = new HashMap<>();

    public static <K, V> HashMapCreator<K, V> builder() {
        return new HashMapCreator<K, V>();
    }

    public static HashMapCreator<String, String> getStringStringBuilder() {
        return new HashMapCreator<>();
    }
    public static HashMapCreator<String, Object> getStringObjectBuilder() {
        return new HashMapCreator<>();
    }

    public static <K, V> HashMapCreator<K, V> getInstance(Map<K, V> map) {
        HashMapCreator<K, V> hashMapCreator = new HashMapCreator<>();
        hashMapCreator.map = map;
        return hashMapCreator;
    }

    public HashMapCreator<K, V> put(K key, V value) {
        this.map.put(key, value);
        return this;
    }

    public HashMapCreator<K, V> putAll(Map<K, V> map) {
        this.map.putAll(map);
        return this;
    }

    public Map<K, V> build() {
        return this.map;
    }
}
