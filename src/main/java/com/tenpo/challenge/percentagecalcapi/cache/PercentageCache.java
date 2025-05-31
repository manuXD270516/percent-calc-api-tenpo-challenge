package com.tenpo.challenge.percentagecalcapi.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PercentageCache {

    private final CacheManager cacheManager;
    private static final String CACHE_NAME = "percentageCache";
    private static final String CACHE_KEY = "currentPercentage";

    public void save(double percentage) {
        getCache().put(CACHE_KEY, percentage);
    }

    public Double get() {
        return getCache().get(CACHE_KEY, Double.class);
    }

    private Cache getCache() {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        if (cache == null) {
            throw new IllegalStateException("Caffeine cache not available");
        }
        return cache;
    }
}
