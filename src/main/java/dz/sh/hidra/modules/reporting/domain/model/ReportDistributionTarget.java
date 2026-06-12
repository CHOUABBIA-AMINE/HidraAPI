/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportDistributionTarget
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Target where a published report should be distributed.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.value.*;
import java.time.Instant;

    /**
     * Target where a published report should be distributed.
     *
         * @param id id
     * @param reportDefinitionId reportDefinitionId
     * @param targetType targetType
     * @param targetReference targetReference
     * @param channelId channelId
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ReportDistributionTarget(
            String id,
        String reportDefinitionId,
        ReportDistributionTargetType targetType,
        String targetReference,
        String channelId,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ReportDistributionTarget {
        id = normalize(id);
        reportDefinitionId = normalize(reportDefinitionId);
        targetReference = normalize(targetReference);
        channelId = normalize(channelId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
