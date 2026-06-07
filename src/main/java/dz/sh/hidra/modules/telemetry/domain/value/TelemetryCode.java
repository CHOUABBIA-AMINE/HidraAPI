/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Validated telemetry business code value object.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import java.util.Locale;
import java.util.regex.Pattern;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Stable business code for telemetry sources, devices, points, catalogs, batches, and external tags.
 *
 * <p>Validation:
 * Codes are trimmed, upper-cased, must be 2 to 120 characters long, may contain uppercase letters,
 * digits, underscores, hyphens, colons, slashes, or dots, and must start with a letter or digit.
 *
 * @param value validated telemetry business code
 */
public record TelemetryCode(String value) implements ValueObject {

    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[A-Z0-9][A-Z0-9_.:/-]*$");

    public TelemetryCode {
        value = normalize(value);
    }

    public static TelemetryCode of(String value) {
        return new TelemetryCode(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("TelemetryCode must not be null or blank.");
        }

        String normalized = value.trim().toUpperCase(Locale.ROOT);

        if (normalized.length() < 2 || normalized.length() > 120) {
            throw new InvalidValueObjectException("TelemetryCode length must be between 2 and 120 characters.");
        }

        if (!ALLOWED_PATTERN.matcher(normalized).matches()) {
            throw new InvalidValueObjectException("TelemetryCode contains unsupported characters.");
        }

        return normalized;
    }
}
