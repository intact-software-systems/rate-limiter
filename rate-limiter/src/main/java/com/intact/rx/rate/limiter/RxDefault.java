package com.intact.rx.rate.limiter;

import java.util.concurrent.atomic.AtomicReference;

import com.intact.rx.rate.limiter.id.CacheHandle;
import com.intact.rx.rate.limiter.id.DomainCacheId;
import com.intact.rx.rate.limiter.id.MasterCacheId;


public class RxDefault {
    private static final AtomicReference<DomainCacheId> defaultRxCommandDomainCacheId = new AtomicReference<>(new DomainCacheId("RxCommand.DefaultRxCommandCacheId"));

    private static final CacheHandle globalCircuitCacheHandle = CacheHandle.create(defaultRxCommandDomainCacheId.get(), MasterCacheId.create("Rx.globalCircuitBreakerScope"), RateLimiterPolicy.class);

    public static CacheHandle getGlobalRateLimiterCacheHandle() {
        return globalCircuitCacheHandle;
    }

    public static DomainCacheId getDefaultRxCommandDomainCacheId() {
        return defaultRxCommandDomainCacheId.get();
    }
}
