/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Normalized telemetry code.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;

import java.util.Locale;

/**
 * Normalized telemetry code.
 *
 * @param value normalized code
 */
public record TelemetryCode(String value) {

    public TelemetryCode {
        if (value == null || value.isBlank()) {
            throw new InvalidTelemetryValueException("Telemetry code must not be null or blank.");
        }
        value = value.trim().toUpperCase(Locale.ROOT);
    }

    public static TelemetryCode of(String value) {
        return new TelemetryCode(value);
    }
}
