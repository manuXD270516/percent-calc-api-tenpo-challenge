package com.tenpo.challenge.percentagecalcapi.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.mockito.Mockito.*;

class PercentageCacheTest {

    private CacheManager cacheManager;
    private Cache cache;
    private PercentageCache percentageCache;

    private static final String CACHE_NAME = "percentageCache";
    private static final String CACHE_KEY = "currentPercentage";

    @BeforeEach
    void setUp() {
        cacheManager = mock(CacheManager.class);
        cache = mock(Cache.class);

        when(cacheManager.getCache(CACHE_NAME)).thenReturn(cache);
        percentageCache = new PercentageCache(cacheManager);
    }

    @Test
    void shouldSavePercentageToCache() {
        percentageCache.save(7.5);

        verify(cache).put(CACHE_KEY, 7.5);
    }

    @Test
    void shouldRetrievePercentageFromCache() {
        when(cache.get(CACHE_KEY, Double.class)).thenReturn(7.5);

        Double result = percentageCache.get();

        assertEquals(7.5, result);
    }

    @Test
    void shouldThrowExceptionWhenCacheNotAvailable() {
        when(cacheManager.getCache(CACHE_NAME)).thenReturn(null);

        PercentageCache brokenCache = new PercentageCache(cacheManager);

        IllegalStateException exception = assertThrows(IllegalStateException.class, brokenCache::get);

        assertEquals("Caffeine cache not available", exception.getMessage());
    }
}
