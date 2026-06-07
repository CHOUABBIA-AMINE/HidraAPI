/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryLocale
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Validated telemetry locale value object.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

import java.util.Locale;
import java.util.Set;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Supported locale for localized telemetry catalog labels.
 *
 * @param value locale code
 */
public record TelemetryLocale(String value) implements ValueObject {

    private static final Set<String> SUPPORTED_LOCALES = Set.of("ar", "fr", "en");

    public TelemetryLocale {
        value = normalize(value);
    }

    public static TelemetryLocale of(String value) {
        return new TelemetryLocale(value);
    }

    public static TelemetryLocale defaultLocale() {
        return new TelemetryLocale("fr");
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return "fr";
        }

        String normalized = value.trim().toLowerCase(Locale.ROOT);

        int regionSeparator = normalized.indexOf('-');
        if (regionSeparator > 0) {
            normalized = normalized.substring(0, regionSeparator);
        }

        if (!SUPPORTED_LOCALES.contains(normalized)) {
            throw new InvalidValueObjectException("TelemetryLocale must be one of ar, fr, or en.");
        }

        return normalized;
    }
}
