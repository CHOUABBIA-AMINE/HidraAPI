/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Position
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Operational position or function.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.value.*;
import java.time.Instant;

    /**
     * Operational position or function.
     *
         * @param id id
     * @param code code
     * @param titleAr titleAr
     * @param titleFr titleFr
     * @param titleEn titleEn
     * @param level level
     * @param description description
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record Position(
            String id,
        String code,
        String titleAr,
        String titleFr,
        String titleEn,
        PositionLevel level,
        String description,
        PositionStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public Position {
        id = normalize(id);
        code = normalize(code);
        titleAr = normalize(titleAr);
        titleFr = normalize(titleFr);
        titleEn = normalize(titleEn);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
