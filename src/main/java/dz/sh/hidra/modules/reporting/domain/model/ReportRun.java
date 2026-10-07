/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportRun
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Execution attempt of a report request.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
import dz.sh.hidra.modules.reporting.domain.value.*;
import java.time.Instant;

    /**
     * Execution attempt of a report request.
     *
         * @param id id
     * @param reportRequestId reportRequestId
     * @param reportDefinitionId reportDefinitionId
     * @param templateVersionId templateVersionId
     * @param status status
     * @param runMode runMode
     * @param queuedAt queuedAt
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param failedAt failedAt
     * @param failureReason failureReason
     * @param recordCount recordCount
     * @param outputCount outputCount
     * @param executionDurationMs executionDurationMs
     * @param correlationId correlationId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ReportRun(
            String id,
        String reportRequestId,
        String reportDefinitionId,
        String templateVersionId,
        ReportRunStatus status,
        ReportRunMode runMode,
        Instant queuedAt,
        Instant startedAt,
        Instant completedAt,
        Instant failedAt,
        String failureReason,
        Long recordCount,
        Long outputCount,
        Long executionDurationMs,
        String correlationId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ReportRun {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidReportingValueException("ReportRun id must not be blank.");
        }
        // HRA-051 required: reportRequestId
        if (reportRequestId == null || reportRequestId.isBlank()) {
            throw new InvalidReportingValueException("ReportRun report request id must not be blank.");
        }
        // HRA-051 required: reportDefinitionId
        if (reportDefinitionId == null || reportDefinitionId.isBlank()) {
            throw new InvalidReportingValueException("ReportRun report definition id must not be blank.");
        }
        // HRA-051 required: templateVersionId
        if (templateVersionId == null || templateVersionId.isBlank()) {
            throw new InvalidReportingValueException("ReportRun template version id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidReportingValueException("ReportRun status must not be null.");
        }
        // HRA-051 required: runMode
        if (runMode == null) {
            throw new InvalidReportingValueException("ReportRun run mode must not be null.");
        }
        // HRA-051 required: queuedAt
        if (queuedAt == null) {
            throw new InvalidReportingValueException("ReportRun queued at must not be null.");
        }

        id = normalize(id);
        reportRequestId = normalize(reportRequestId);
        reportDefinitionId = normalize(reportDefinitionId);
        templateVersionId = normalize(templateVersionId);
        failureReason = normalize(failureReason);
        if(status == ReportRunStatus.COMPLETED && completedAt == null)
            throw new InvalidReportingValueException("Completed ReportRun requires completedAt.");
        if(status == ReportRunStatus.FAILED && failureReason == null)
            throw new InvalidReportingValueException("Failed ReportRun requires failureReason.");
        correlationId = normalize(correlationId);
        }
        public boolean terminalStatus() {
            return status == ReportRunStatus.COMPLETED
                    || status == ReportRunStatus.FAILED
                    || status == ReportRunStatus.CANCELLED
                    || status == ReportRunStatus.EXPIRED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
