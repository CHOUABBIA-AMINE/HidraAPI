/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportDistributionRecord
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Distribution request and outcome linked to a report artifact.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.value.*;
import java.time.Instant;

    /**
     * Distribution request and outcome linked to a report artifact.
     *
         * @param id id
     * @param reportPublicationId reportPublicationId
     * @param reportOutputArtifactId reportOutputArtifactId
     * @param targetType targetType
     * @param targetReference targetReference
     * @param notificationRequestId notificationRequestId
     * @param integrationOutboundRecordId integrationOutboundRecordId
     * @param status status
     * @param requestedAt requestedAt
     * @param completedAt completedAt
     * @param failureReason failureReason
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ReportDistributionRecord(
            String id,
        String reportPublicationId,
        String reportOutputArtifactId,
        ReportDistributionTargetType targetType,
        String targetReference,
        String notificationRequestId,
        String integrationOutboundRecordId,
        ReportDistributionStatus status,
        Instant requestedAt,
        Instant completedAt,
        String failureReason,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ReportDistributionRecord {
        id = normalize(id);
        reportPublicationId = normalize(reportPublicationId);
        reportOutputArtifactId = normalize(reportOutputArtifactId);
        targetReference = normalize(targetReference);
        notificationRequestId = normalize(notificationRequestId);
        integrationOutboundRecordId = normalize(integrationOutboundRecordId);
        failureReason = normalize(failureReason);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
