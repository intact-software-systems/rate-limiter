package com.intact.rx.rate.limiter;

public interface RateLimiter {
    /**
     * @return true if request is allowed according to current status and configured policy. Mutating function.
     */
    boolean allowRequest();

    /**
     * @param onViolatedFrequency callback
     * @return this
     */
    RateLimiter onViolatedFrequencyDo(VoidStrategy1<RateLimiterId> onViolatedFrequency);

    /**
     * @param onViolatedQuota callback
     * @return this
     */
    RateLimiter onViolatedQuotaDo(VoidStrategy1<RateLimiterId> onViolatedQuota);

    /**
     * disconnect all attached observers
     */
    void disconnectAll();
}
