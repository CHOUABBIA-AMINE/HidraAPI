/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReadinessStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.observability
 *
 * @Description : Represents a technical readiness check result.
 *
 */
package dz.sh.hidra.platform.observability;

import java.time.Instant;
import java.util.Objects;

/**
 * Technical readiness check result.
 *
 * @param ready whether the component is ready
 * @param component technical component name
 * @param message optional message
 * @param checkedAt check timestamp
 */
public record ReadinessStatus(
        boolean ready,
        String component,
        String message,
        Instant checkedAt
) {

    public ReadinessStatus {
        component = requireText(component, "Readiness component must not be null or blank.");
        message = normalize(message);
        Objects.requireNonNull(checkedAt, "Readiness check timestamp must not be null.");
    }

    public static ReadinessStatus ready(String component) {
        return new ReadinessStatus(true, component, null, Instant.now());
    }

    public static ReadinessStatus notReady(String component, String message) {
        return new ReadinessStatus(false, component, message, Instant.now());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
