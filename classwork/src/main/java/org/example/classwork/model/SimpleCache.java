package org.example.classwork.model;

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

    public void put (String key, CountryListResponse response) {
        cache.put(key, new CacheData(response));
    }

    public CountryListResponse get (String key) {
        CacheData cacheData = cache.get(key);

        if (cacheData == null) {
            return null;
        }

        if (cacheData.isExpired()) {
            cache.remove(key);
            return null;
        }

        return cacheData.data;
    }
}
