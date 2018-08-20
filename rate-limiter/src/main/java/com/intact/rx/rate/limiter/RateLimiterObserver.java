package com.intact.rx.rate.limiter;

public interface RateLimiterObserver {
    void onViolatedFrequency(RateLimiterId rateLimiterId);

    void onViolatedQuota(RateLimiterId rateLimiterId);
}
