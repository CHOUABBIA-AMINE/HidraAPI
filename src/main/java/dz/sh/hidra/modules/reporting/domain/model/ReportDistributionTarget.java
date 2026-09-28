/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportDistributionTarget
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidReportingValueException("ReportDistributionTarget id must not be blank.");
        }
        // HRA-051 required: reportDefinitionId
        if (reportDefinitionId == null || reportDefinitionId.isBlank()) {
            throw new InvalidReportingValueException("ReportDistributionTarget report definition id must not be blank.");
        }
        // HRA-051 required: targetType
        if (targetType == null) {
            throw new InvalidReportingValueException("ReportDistributionTarget target type must not be null.");
        }

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
