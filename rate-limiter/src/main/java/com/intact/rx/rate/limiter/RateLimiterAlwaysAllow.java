package com.intact.rx.rate.limiter;

public class RateLimiterAlwaysAllow implements RateLimiter {
    public static final RateLimiterAlwaysAllow instance = new RateLimiterAlwaysAllow();

    @Override
    public boolean allowRequest() {
        return true;
    }

    @Override
    public RateLimiter onViolatedFrequencyDo(VoidStrategy1<RateLimiterId> onViolated) {
        return this;
    }

    @Override
    public RateLimiter onViolatedQuotaDo(VoidStrategy1<RateLimiterId> onViolatedQuota) {
        return this;
    }

    @Override
    public void disconnectAll() {
    }
}
