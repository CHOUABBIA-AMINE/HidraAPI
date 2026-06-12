/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DateRange
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.value
 *
 * @Description : Represents an immutable date-only range.
 *
 */
package dz.sh.hidra.kernel.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;
import dz.sh.hidra.kernel.exception.InvalidValueObjectException;

import java.time.LocalDate;

/**
 * Immutable date-only range using inclusive boundaries.
 *
 * @param start start date, inclusive
 * @param end end date, inclusive; null means open-ended
 */
public record DateRange(LocalDate start, LocalDate end) implements ValueObject {

    public DateRange {
        if (start == null) {
            throw new InvalidValueObjectException("Date range start must not be null.");
        }
        if (end != null && end.isBefore(start)) {
            throw new InvalidValueObjectException("Date range end must not be before start.");
        }
    }

    /**
     * Creates a finite date range.
     *
     * @param start start date, inclusive
     * @param end end date, inclusive
     * @return closed date range
     */
    public static DateRange closed(LocalDate start, LocalDate end) {
        if (end == null) {
            throw new InvalidValueObjectException("Closed date range end must not be null.");
        }
        return new DateRange(start, end);
    }

    /**
     * Creates an open-ended date range.
     *
     * @param start start date, inclusive
     * @return open-ended date range
     */
    public static DateRange openEnded(LocalDate start) {
        return new DateRange(start, null);
    }

    /**
     * Checks whether the provided date is inside the range.
     *
     * @param date date to check
     * @return true when the date is inside the range
     */
    public boolean contains(LocalDate date) {
        if (date == null || date.isBefore(start)) {
            return false;
        }
        return end == null || !date.isAfter(end);
    }
}
