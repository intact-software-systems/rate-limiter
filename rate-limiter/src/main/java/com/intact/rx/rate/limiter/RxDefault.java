package com.intact.rx.rate.limiter;

import java.util.concurrent.atomic.AtomicReference;

import com.intact.rx.rate.limiter.id.CacheHandle;
import com.intact.rx.rate.limiter.id.DomainCacheId;
import com.intact.rx.rate.limiter.id.MasterCacheId;


public class RxDefault {
    private static final AtomicReference<DomainCacheId> defaultRxCommandDomainCacheId = new AtomicReference<>(new DomainCacheId("RxCommand.DefaultRxCommandCacheId"));

    private static final CacheHandle globalRateLimiterCacheHandle = CacheHandle.create(getDefaultRxCommandDomainCacheId(), MasterCacheId.create("Rx.globalRateLimiterScope"), RateLimiterPolicy.class);

    public static CacheHandle getGlobalRateLimiterCacheHandle() {
        return globalRateLimiterCacheHandle;
    }

    public static DomainCacheId getDefaultRxCommandDomainCacheId() {
        return defaultRxCommandDomainCacheId.get();
    }
}
