/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DateRange
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.value
 *
 * @Description : Immutable date-only range using LocalDate boundaries.
 *
 */
package dz.sh.hidra.kernel.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

import java.time.LocalDate;

public final class DateRange implements ValueObject {

    private final LocalDate start;
    private final LocalDate end;

    private DateRange(LocalDate start, LocalDate end) {
        if (start == null) {
            throw new InvalidValueObjectException("DateRange start must not be null.");
        }
        if (end != null && end.isBefore(start)) {
            throw new InvalidValueObjectException("DateRange end must not be before start.");
        }
        this.start = start;
        this.end = end;
    }

    public static DateRange closed(LocalDate start, LocalDate end) {
        if (end == null) {
            throw new InvalidValueObjectException("DateRange closed end must not be null.");
        }
        return new DateRange(start, end);
    }

    public static DateRange openEnded(LocalDate start) {
        return new DateRange(start, null);
    }

    public boolean contains(LocalDate date) {
        if (date == null) {
            return false;
        }
        return !date.isBefore(start) && (end == null || !date.isAfter(end));
    }

    public LocalDate start() {
        return start;
    }

    public LocalDate end() {
        return end;
    }
}
