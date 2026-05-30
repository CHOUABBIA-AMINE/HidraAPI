/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DateRangeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.value
 *
 * @Description : Verifies valid and invalid date ranges.
 *
 */
package dz.sh.hidra.kernel.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DateRangeTest {

    @Test
    void shouldCreateClosedRangeAndCheckContainment() {
        DateRange range = DateRange.closed(LocalDate.parse("2026-01-01"), LocalDate.parse("2026-01-31"));

        assertThat(range.contains(LocalDate.parse("2026-01-01"))).isTrue();
        assertThat(range.contains(LocalDate.parse("2026-01-15"))).isTrue();
        assertThat(range.contains(LocalDate.parse("2026-02-01"))).isFalse();
    }

    @Test
    void shouldCreateOpenEndedRange() {
        DateRange range = DateRange.openEnded(LocalDate.parse("2026-01-01"));

        assertThat(range.end()).isNull();
        assertThat(range.contains(LocalDate.parse("2027-01-01"))).isTrue();
    }

    @Test
    void shouldRejectEndBeforeStart() {
        assertThatThrownBy(() -> DateRange.closed(LocalDate.parse("2026-02-01"), LocalDate.parse("2026-01-01")))
                .isInstanceOf(InvalidValueObjectException.class)
                .hasMessage("DateRange end must not be before start.");
    }
}
