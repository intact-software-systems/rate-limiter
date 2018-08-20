package com.intact.rx.rate.limiter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intact.rx.rate.limiter.id.CacheHandle;

public class RateLimiterCache implements RateLimiterObserver {
    private static final Logger log = LoggerFactory.getLogger(RateLimiterCache.class);

    private static final RateLimiterSubject rateLimiterSubject = new RateLimiterSubject();
    private static final RateLimiterCache instance = new RateLimiterCache();
    private static final Map<CacheHandle, Map<RateLimiterId, RateLimiter>> rateLimiterCache = new ConcurrentHashMap<>();

    public static Map<CacheHandle, Map<RateLimiterId, RateLimiter>> rateLimiterCache() {
        return rateLimiterCache;
    }

    public static RateLimiterSubject observeAll() {
        return rateLimiterSubject;
    }

    public static RateLimiter rateLimiter(RateLimiterId rateLimiterId, RateLimiterPolicy rateLimiterPolicy) {
        if (rateLimiterId.isNone() || rateLimiterPolicy.isUnlimited()) {
            return RateLimiterAlwaysAllow.instance;
        }

        return cache(rateLimiterId.getCacheHandle())
                .computeIfAbsent(
                        rateLimiterId,
                        id -> new RxRateLimiter(rateLimiterId, rateLimiterPolicy)
                                .onViolatedFrequencyDo(instance::onViolatedFrequency)
                                .onViolatedQuotaDo(instance::onViolatedQuota)
                );
    }

    public static Optional<RateLimiter> find(RateLimiterId rateLimiterId) {
        return Optional.ofNullable(cache(rateLimiterId.getCacheHandle()).get(rateLimiterId));
    }

    private static Map<RateLimiterId, RateLimiter> cache(CacheHandle handle) {
        return rateLimiterCache.computeIfAbsent(handle, cacheHandle -> new ConcurrentHashMap<>());
    }

    // --------------------------------------------
    // Interface RateLimiterObserver
    // --------------------------------------------

    @Override
    public void onViolatedFrequency(RateLimiterId rateLimiterId) {
        log.info("Rate limit by frequency violated for {}", rateLimiterId);
        rateLimiterSubject.onViolatedFrequency(rateLimiterId);
    }

    @Override
    public void onViolatedQuota(RateLimiterId rateLimiterId) {
        log.info("Rate limit by quota violated for {}", rateLimiterId);
        rateLimiterSubject.onViolatedQuota(rateLimiterId);
    }
}
