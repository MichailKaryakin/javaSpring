package org.example.classwork.model;

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
        CountryListResponse data;
        long time;

        public CacheData(CountryListResponse data) {
            this.data = data;
            time = System.currentTimeMillis();
        }

        boolean isExpired() {
            return System.currentTimeMillis() - time > timeToLive;
        }
    }

    public void put(String key, CountryListResponse response) {
        cache.put(key, new CacheData(response));
        System.out.println("Добавлено в кэш: " + key);
    }

    public CountryListResponse get(String key) {
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
        return cacheData.data;
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
            info.put("countriesCount",
                    data.data != null && data.data.getCountries() != null
                            ? data.data.getCountries().size()
                            : 0);

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
