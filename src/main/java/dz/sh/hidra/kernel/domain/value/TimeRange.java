/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TimeRange
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.value
 *
 * @Description : Immutable timestamp range using Instant boundaries.
 *
 */
package dz.sh.hidra.kernel.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

import java.time.Instant;

public final class TimeRange implements ValueObject {

    private final Instant start;
    private final Instant end;

    private TimeRange(Instant start, Instant end) {
        if (start == null) {
            throw new InvalidValueObjectException("TimeRange start must not be null.");
        }
        if (end != null && end.isBefore(start)) {
            throw new InvalidValueObjectException("TimeRange end must not be before start.");
        }
        this.start = start;
        this.end = end;
    }

    public static TimeRange closed(Instant start, Instant end) {
        if (end == null) {
            throw new InvalidValueObjectException("TimeRange closed end must not be null.");
        }
        return new TimeRange(start, end);
    }

    public static TimeRange openEnded(Instant start) {
        return new TimeRange(start, null);
    }

    public boolean contains(Instant instant) {
        if (instant == null) {
            return false;
        }
        return !instant.isBefore(start) && (end == null || !instant.isAfter(end));
    }

    public Instant start() {
        return start;
    }

    public Instant end() {
        return end;
    }
}
