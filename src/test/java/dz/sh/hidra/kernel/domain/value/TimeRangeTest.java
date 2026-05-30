/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TimeRangeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.value
 *
 * @Description : Verifies valid and invalid time ranges.
 *
 */
package dz.sh.hidra.kernel.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimeRangeTest {

    @Test
    void shouldCreateClosedRangeAndCheckContainment() {
        TimeRange range = TimeRange.closed(Instant.parse("2026-01-01T00:00:00Z"), Instant.parse("2026-01-01T01:00:00Z"));

        assertThat(range.contains(Instant.parse("2026-01-01T00:00:00Z"))).isTrue();
        assertThat(range.contains(Instant.parse("2026-01-01T00:30:00Z"))).isTrue();
        assertThat(range.contains(Instant.parse("2026-01-01T02:00:00Z"))).isFalse();
    }

    @Test
    void shouldCreateOpenEndedRange() {
        TimeRange range = TimeRange.openEnded(Instant.parse("2026-01-01T00:00:00Z"));

        assertThat(range.end()).isNull();
        assertThat(range.contains(Instant.parse("2027-01-01T00:00:00Z"))).isTrue();
    }

    @Test
    void shouldRejectNullStart() {
        assertThatThrownBy(() -> TimeRange.openEnded(null))
                .isInstanceOf(InvalidValueObjectException.class)
                .hasMessage("TimeRange start must not be null.");
    }
}
