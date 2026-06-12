/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.value
 *
 * @Description : Stable incident identifier.
 *
 */
package dz.sh.hidra.modules.incident.domain.value;

import dz.sh.hidra.modules.incident.domain.exception.InvalidIncidentValueException;

import java.util.UUID;

/**
 * Stable incident identifier.
 *
 * @param value identifier value
 */
public record IncidentId(String value) {

    public IncidentId {
        value = requireText(value, "Incident ID must not be null or blank.");
    }

    public static IncidentId of(String value) {
        return new IncidentId(value);
    }

    public static IncidentId newId() {
        return new IncidentId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidIncidentValueException(message);
        }
        return value.trim();
    }
}
