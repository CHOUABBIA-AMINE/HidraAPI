/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.value
 *
 * @Description : Stable planning identifier.
 *
 */
package dz.sh.hidra.modules.planning.domain.value;

import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;

import java.util.UUID;

/**
 * Stable planning identifier.
 *
 * @param value identifier value
 */
public record PlanningId(String value) {

    public PlanningId {
        value = requireText(value, "Planning ID must not be null or blank.");
    }

    public static PlanningId of(String value) {
        return new PlanningId(value);
    }

    public static PlanningId newId() {
        return new PlanningId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidPlanningValueException(message);
        }
        return value.trim();
    }
}
