/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportDistributionRecord
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidReportingValueException("ReportDistributionRecord id must not be blank.");
        }
        // HRA-051 required: reportPublicationId
        if (reportPublicationId == null || reportPublicationId.isBlank()) {
            throw new InvalidReportingValueException("ReportDistributionRecord report publication id must not be blank.");
        }
        // HRA-051 required: reportOutputArtifactId
        if (reportOutputArtifactId == null || reportOutputArtifactId.isBlank()) {
            throw new InvalidReportingValueException("ReportDistributionRecord report output artifact id must not be blank.");
        }
        // HRA-051 required: targetType
        if (targetType == null) {
            throw new InvalidReportingValueException("ReportDistributionRecord target type must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidReportingValueException("ReportDistributionRecord status must not be null.");
        }
        // HRA-051 required: requestedAt
        if (requestedAt == null) {
            throw new InvalidReportingValueException("ReportDistributionRecord requested at must not be null.");
        }

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
