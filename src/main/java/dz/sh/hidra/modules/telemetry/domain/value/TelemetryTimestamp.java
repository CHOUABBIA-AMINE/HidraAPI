/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryTimestamp
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Validated telemetry timestamp value object.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Timestamp wrapper for source, received, validity, and audit timestamps.
 *
 * @param value instant timestamp
 */
public record TelemetryTimestamp(Instant value) implements ValueObject {

    public TelemetryTimestamp {
        value = Objects.requireNonNull(value, "TelemetryTimestamp value must not be null.");

        if (value.isAfter(Instant.now().plusSeconds(300))) {
            throw new InvalidValueObjectException("TelemetryTimestamp must not be more than five minutes in the future.");
        }
    }

    public static TelemetryTimestamp of(Instant value) {
        return new TelemetryTimestamp(value);
    }

    public static TelemetryTimestamp now() {
        return new TelemetryTimestamp(Instant.now());
    }

    public boolean isBefore(TelemetryTimestamp other) {
        return value.isBefore(Objects.requireNonNull(other, "other timestamp must not be null.").value());
    }

    public boolean isAfter(TelemetryTimestamp other) {
        return value.isAfter(Objects.requireNonNull(other, "other timestamp must not be null.").value());
    }
}
