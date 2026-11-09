package dz.sh.hidra.modules.identity.application.port.out;

/** Anonymous refresh request admission, independent of the token/session authority owner. */
public interface RefreshAttemptThrottlePort {
    /** Deny excessive requests before database token consumption or issuance. */
    void check(String opaqueRefreshToken, String serverObservedClientAddress);

    /** A non-disclosing overload result, mapped to HTTP 429 by the REST adapter. */
    final class Limited extends RuntimeException {
        public Limited() { super("Refresh temporarily unavailable.", null, false, false); }
    }
}
