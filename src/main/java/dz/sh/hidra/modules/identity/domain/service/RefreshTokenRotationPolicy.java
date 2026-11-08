package dz.sh.hidra.modules.identity.domain.service;

import java.time.Instant;

/** Absolute family expiry, with no sliding extension or external IdP refresh. */
public final class RefreshTokenRotationPolicy {
    public static final long DEFAULT_SECONDS = 604800;
    public static final long MAX_SECONDS = 2592000;
    private RefreshTokenRotationPolicy() {}
    public static Instant deadline(Instant started, long seconds) {
        if (started == null || seconds < 300 || seconds > MAX_SECONDS)
            throw new IllegalArgumentException("Invalid refresh family lifetime.");
        return started.plusSeconds(seconds);
    }
    public static void requireLocal(String type) {
        if (!"LOCAL".equals(type))
            throw new IllegalArgumentException("External providers must reauthenticate.");
    }
    /** Invalidated families commit their revocation while denying renewal. */
    public static final class Rejected extends RuntimeException {
        public Rejected() { super("Invalid refresh credentials."); }
    }
    /** Committed revocation is preserved when a previously consumed token is reused. */
    public static final class Replay extends RuntimeException {
        public Replay() { super("Invalid refresh credentials."); }
    }
}
