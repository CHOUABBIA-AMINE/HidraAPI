/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Shift
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Shift definition.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.value.*;
import java.time.Instant;

    /**
     * Shift definition.
     *
         * @param id id
     * @param code code
     * @param name name
     * @param shiftType shiftType
     * @param startTime startTime
     * @param endTime endTime
     * @param timezone timezone
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record Shift(
            String id,
        String code,
        String name,
        ShiftType shiftType,
        String startTime,
        String endTime,
        String timezone,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public Shift {
        id = normalize(id);
        code = normalize(code);
        name = normalize(name);
        startTime = normalize(startTime);
        endTime = normalize(endTime);
        timezone = normalize(timezone);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
