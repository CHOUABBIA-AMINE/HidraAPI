/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryId
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Stable telemetry identifier.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;

import java.util.UUID;

/**
 * Stable telemetry identifier.
 *
 * @param value identifier value
 */
public record TelemetryId(String value) {

    public TelemetryId {
        value = requireText(value, "Telemetry ID must not be null or blank.");
    }

    public static TelemetryId of(String value) {
        return new TelemetryId(value);
    }

    public static TelemetryId newId() {
        return new TelemetryId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidTelemetryValueException(message);
        }
        return value.trim();
    }
}
