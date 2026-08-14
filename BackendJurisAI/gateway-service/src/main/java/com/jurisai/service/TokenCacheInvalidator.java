package com.jurisai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCacheInvalidator {
    private final CacheManager cacheManager;

    public void invalidate(String message) {
        Cache tokenCache = cacheManager.getCache("validatedTokens");
        if (tokenCache != null) {
            tokenCache.clear();
        }
    }
}
