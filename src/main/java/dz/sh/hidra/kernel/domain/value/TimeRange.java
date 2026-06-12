/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TimeRange
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.value
 *
 * @Description : Represents an immutable instant range.
 *
 */
package dz.sh.hidra.kernel.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;
import dz.sh.hidra.kernel.exception.InvalidValueObjectException;

import java.time.Instant;

/**
 * Immutable timestamp range using inclusive boundaries.
 *
 * @param start start instant, inclusive
 * @param end end instant, inclusive; null means open-ended
 */
public record TimeRange(Instant start, Instant end) implements ValueObject {

    public TimeRange {
        if (start == null) {
            throw new InvalidValueObjectException("Time range start must not be null.");
        }
        if (end != null && end.isBefore(start)) {
            throw new InvalidValueObjectException("Time range end must not be before start.");
        }
    }

    /**
     * Creates a finite instant range.
     *
     * @param start start instant, inclusive
     * @param end end instant, inclusive
     * @return closed time range
     */
    public static TimeRange closed(Instant start, Instant end) {
        if (end == null) {
            throw new InvalidValueObjectException("Closed time range end must not be null.");
        }
        return new TimeRange(start, end);
    }

    /**
     * Creates an open-ended instant range.
     *
     * @param start start instant, inclusive
     * @return open-ended time range
     */
    public static TimeRange openEnded(Instant start) {
        return new TimeRange(start, null);
    }

    /**
     * Checks whether the provided instant is inside the range.
     *
     * @param instant instant to check
     * @return true when the instant is inside the range
     */
    public boolean contains(Instant instant) {
        if (instant == null || instant.isBefore(start)) {
            return false;
        }
        return end == null || !instant.isAfter(end);
    }
}
