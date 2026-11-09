package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.port.out.RefreshAttemptThrottlePort;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * HAPI-929: single-process, bounded admission of anonymous LOCAL refresh attempts.
 * A server-observed address AND opaque family ID form the bucket: one company NAT
 * does not become one shared operator quota. No credentials, raw family IDs,
 * usernames or user-supplied forwarding headers are retained.
 *
 * A single application process is the supported first-deployment topology.
 * Cluster-wide enforcement requires a separately accepted shared rate store.
 */
@Component
public final class InMemoryRefreshAttemptThrottleAdapter implements RefreshAttemptThrottlePort {
    private record Window(long untilMillis, int attempts) {}
    private final boolean enabled;
    private final int maxAttempts;
    private final long windowMillis;
    private final int maxKeys;
    private final Clock clock;
    private final Map<String, Window> windows = new HashMap<>();

    public InMemoryRefreshAttemptThrottleAdapter(
            @Value("${hidra.identity.refresh.throttle.enabled:true}") boolean enabled,
            @Value("${hidra.identity.refresh.throttle.max-attempts:30}") int maxAttempts,
            @Value("${hidra.identity.refresh.throttle.window-seconds:60}") long windowSeconds,
            @Value("${hidra.identity.refresh.throttle.max-keys:32768}") int maxKeys,
            @Qualifier("identityAuthenticationClock") Clock clock) {
        if (maxAttempts < 1 || maxAttempts > 1000
                || windowSeconds < 1 || windowSeconds > 3600
                || maxKeys < 1 || maxKeys > 65536) {
            throw new IllegalStateException("Invalid Identity refresh throttle configuration.");
        }
        this.enabled = enabled;
        this.maxAttempts = maxAttempts;
        this.windowMillis = Math.multiplyExact(windowSeconds, 1000);
        this.maxKeys = maxKeys;
        this.clock = Objects.requireNonNull(clock);
    }

    @Override
    public synchronized void check(String opaqueRefreshToken, String serverObservedClientAddress) {
        if (!enabled) return;
        String key = key(opaqueRefreshToken, serverObservedClientAddress);
        long now = clock.millis();
        Window prior = windows.get(key);
        if (prior != null && now >= prior.untilMillis()) {
            windows.remove(key);
            prior = null;
        }
        if (prior == null) {
            if (windows.size() >= maxKeys) {
                // Only expired buckets may be discarded. Active quotas never reset
                // merely because another client submitted many distinct family IDs.
                Iterator<Map.Entry<String, Window>> it = windows.entrySet().iterator();
                while (it.hasNext()) {
                    if (now >= it.next().getValue().untilMillis()) it.remove();
                }
                if (windows.size() >= maxKeys) throw new Limited();
            }
            windows.put(key, new Window(Math.addExact(now, windowMillis), 1));
        } else if (prior.attempts() >= maxAttempts) {
            throw new Limited();
        } else {
            windows.put(key, new Window(prior.untilMillis(), prior.attempts() + 1));
        }
    }

    private static String key(String raw, String address) {
        String family = "invalid";
        if (raw != null && raw.length() >= 37 && raw.charAt(36) == '.') {
            String candidate = raw.substring(0, 36);
            try {
                if (UUID.fromString(candidate).toString().equals(candidate)) family = candidate;
            } catch (IllegalArgumentException ignored) {
                // Unparseable credentials share a single invalid bucket per source.
            }
        }
        // Remote address is taken from the servlet container, NOT X-Forwarded-For.
        String remote = address == null || address.isBlank()
                ? "unattributed" : address.strip();
        if (remote.length() > 128) remote = remote.substring(0, 128);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = (remote + "#" + family).getBytes(StandardCharsets.UTF_8);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest.digest(bytes));
        } catch (NoSuchAlgorithmException failure) {
            throw new IllegalStateException("Refresh throttling unavailable.", failure);
        }
    }
}
