package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.assertj.core.api.Assertions.*;
import dz.sh.hidra.modules.identity.application.port.out.RefreshAttemptThrottlePort;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

/** Pure deterministic tests; PostgreSQL/HTTP coverage resides in the original HAPI-929 suite. */
class InMemoryRefreshAttemptThrottleAdapterTest {
    private static String opaque(String family) { return family + "." + "A".repeat(43); }
    private static final String FIRST = opaque("11111111-1111-1111-1111-111111111111");
    private static final String SECOND = opaque("22222222-2222-2222-2222-222222222222");
    private static final String THIRD = opaque("33333333-3333-3333-3333-333333333333");

    @Test void perFamilySourceQuotaIsNonDisclosing() {
        var limiter = new InMemoryRefreshAttemptThrottleAdapter(true, 2, 60, 10,
                Clock.fixed(Instant.parse("2026-11-09T10:00:00Z"), ZoneOffset.UTC));
        limiter.check(FIRST, "192.0.2.10");
        limiter.check(FIRST, "192.0.2.10");
        assertThatThrownBy(() -> limiter.check(FIRST, "192.0.2.10"))
                .isInstanceOf(RefreshAttemptThrottlePort.Limited.class)
                .hasMessageNotContaining(FIRST);
    }

    @Test void manyOperatorsBehindOneAddressHaveSeparateFamilyQuotas() {
        var limiter = new InMemoryRefreshAttemptThrottleAdapter(true, 1, 60, 10, Clock.systemUTC());
        limiter.check(FIRST, "192.0.2.10");
        limiter.check(SECOND, "192.0.2.10");
        limiter.check(FIRST, "192.0.2.11");
        assertThatThrownBy(() -> limiter.check(FIRST, "192.0.2.10"))
                .isInstanceOf(RefreshAttemptThrottlePort.Limited.class);
    }

    @Test void windowExpiryAllowsTheNextAttempt() {
        var time = new MutableClock();
        var limiter = new InMemoryRefreshAttemptThrottleAdapter(true, 1, 60, 2, time);
        limiter.check(FIRST, "192.0.2.10");
        assertThatThrownBy(() -> limiter.check(FIRST, "192.0.2.10"))
                .isInstanceOf(RefreshAttemptThrottlePort.Limited.class);
        time.advance(61);
        limiter.check(FIRST, "192.0.2.10");
    }

    @Test void boundedStorageFailsClosedWithoutDiscardingActiveQuota() {
        var limiter = new InMemoryRefreshAttemptThrottleAdapter(true, 1, 60, 2,
                Clock.fixed(Instant.parse("2026-11-09T10:00:00Z"), ZoneOffset.UTC));
        limiter.check(FIRST, "192.0.2.10");
        limiter.check(SECOND, "192.0.2.10");
        assertThatThrownBy(() -> limiter.check(THIRD, "192.0.2.10"))
                .isInstanceOf(RefreshAttemptThrottlePort.Limited.class);
        assertThatThrownBy(() -> limiter.check(FIRST, "192.0.2.10"))
                .isInstanceOf(RefreshAttemptThrottlePort.Limited.class);
    }

    @Test void concurrencyCannotAdmitMoreThanConfiguredLimit() throws Exception {
        var limiter = new InMemoryRefreshAttemptThrottleAdapter(true, 3, 60, 10,
                Clock.fixed(Instant.parse("2026-11-09T10:00:00Z"), ZoneOffset.UTC));
        try (var executor = Executors.newFixedThreadPool(12)) {
            var start = new CountDownLatch(1);
            var results = new ArrayList<java.util.concurrent.Future<Boolean>>();
            for (int i = 0; i < 12; i++) {
                results.add(executor.submit(() -> {
                    start.await();
                    try { limiter.check(FIRST, "192.0.2.10"); return true; }
                    catch (RefreshAttemptThrottlePort.Limited denied) { return false; }
                }));
            }
            start.countDown();
            int allowed = 0;
            for (var f : results) if (f.get(15, TimeUnit.SECONDS)) allowed++;
            assertThat(allowed).isEqualTo(3);
        }
    }

    @Test void unsafeConfigurationIsRejectedAtStartup() {
        assertThatThrownBy(() -> new InMemoryRefreshAttemptThrottleAdapter(
                true, 0, 60, 10, Clock.systemUTC())).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> new InMemoryRefreshAttemptThrottleAdapter(
                true, 30, 0, 10, Clock.systemUTC())).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> new InMemoryRefreshAttemptThrottleAdapter(
                true, 30, 60, 65537, Clock.systemUTC())).isInstanceOf(IllegalStateException.class);
    }

    private static final class MutableClock extends Clock {
        private Instant time = Instant.parse("2026-11-09T10:00:00Z");
        @Override public ZoneId getZone() { return ZoneOffset.UTC; }
        @Override public Clock withZone(ZoneId zone) { return this; }
        @Override public Instant instant() { return time; }
        void advance(long seconds) { time = time.plusSeconds(seconds); }
    }
}
