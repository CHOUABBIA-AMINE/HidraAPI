/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLine
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Reporting relation between positions, units, or employees.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.value.*;
import java.time.Instant;

    /**
     * Reporting relation between positions, units, or employees.
     *
         * @param id id
     * @param reportingLineType reportingLineType
     * @param sourceType sourceType
     * @param sourceId sourceId
     * @param targetType targetType
     * @param targetId targetId
     * @param validFrom validFrom
     * @param validTo validTo
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ReportingLine(
            String id,
        ReportingLineType reportingLineType,
        String sourceType,
        String sourceId,
        String targetType,
        String targetId,
        Instant validFrom,
        Instant validTo,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ReportingLine {
        id = normalize(id);
        sourceType = normalize(sourceType);
        sourceId = normalize(sourceId);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
