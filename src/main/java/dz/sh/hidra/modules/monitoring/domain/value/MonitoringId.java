/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.value
 *
 * @Description : Stable monitoring identifier.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.value;

import dz.sh.hidra.modules.monitoring.domain.exception.InvalidMonitoringValueException;

import java.util.UUID;

/**
 * Stable monitoring identifier.
 *
 * @param value identifier value
 */
public record MonitoringId(String value) {

    public MonitoringId {
        value = requireText(value, "Monitoring ID must not be null or blank.");
    }

    public static MonitoringId of(String value) {
        return new MonitoringId(value);
    }

    public static MonitoringId newId() {
        return new MonitoringId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidMonitoringValueException(message);
        }
        return value.trim();
    }
}
