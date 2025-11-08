package org.example.homework7.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleCache {
    private final Map<String, CacheData> cache = new ConcurrentHashMap<>();
    private final long timeToLive;

    public SimpleCache(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    class CacheData {
        Object data;
        long time;

        public CacheData(Object data) {
            this.data = data;
            this.time = System.currentTimeMillis();
        }

        boolean isExpired() {
            return System.currentTimeMillis() - time > timeToLive;
        }
    }

    public void put(String key, Object response) {
        cache.put(key, new CacheData(response));
        System.out.println("Добавлено в кэш: " + key);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        CacheData cacheData = cache.get(key);

        if (cacheData == null) {
            System.out.println("Нет записи в кэше: " + key);
            return null;
        }

        if (cacheData.isExpired()) {
            cache.remove(key);
            System.out.println("Кэш просрочен и удалён: " + key);
            return null;
        }

        System.out.println("Найдено в кэше: " + key);
        return (T) cacheData.data;
    }

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        Map<String, Object> entries = new HashMap<>();

        for (Map.Entry<String, CacheData> entry : cache.entrySet()) {
            CacheData data = entry.getValue();
            long age = System.currentTimeMillis() - data.time;
            long expiresIn = timeToLive - age;
            boolean expired = data.isExpired();

            Map<String, Object> info = new HashMap<>();
            info.put("expired", expired);
            info.put("ageMs", age);
            info.put("expiresInMs", Math.max(expiresIn, 0));
            info.put("type", data.data != null ? data.data.getClass().getSimpleName() : "null");

            try {
                assert data.data != null;
                var method = data.data.getClass().getMethod("getCountries");
                Object countries = method.invoke(data.data);
                if (countries instanceof java.util.List<?> list) {
                    info.put("countriesCount", list.size());
                }
            } catch (Exception ignored) {}

            entries.put(entry.getKey(), info);
        }

        stats.put("entriesCount", entries.size());
        stats.put("timeToLiveMs", timeToLive);
        stats.put("entries", entries);

        return stats;
    }

    public void clear() {
        int sizeBefore = cache.size();
        cache.clear();
        System.out.println("Кэш очищен! Удалено записей: " + sizeBefore);
    }
}
