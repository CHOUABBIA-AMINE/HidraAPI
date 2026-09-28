/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportPublication
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Controlled publication of a generated report.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
import dz.sh.hidra.modules.reporting.domain.value.*;
import java.time.Instant;

    /**
     * Controlled publication of a generated report.
     *
         * @param id id
     * @param reportRunId reportRunId
     * @param publicationStatus publicationStatus
     * @param publishedByActorId publishedByActorId
     * @param publishedByDisplayNameSnapshot publishedByDisplayNameSnapshot
     * @param publishedAt publishedAt
     * @param publicationNote publicationNote
     * @param workflowReferenceId workflowReferenceId
     * @param auditReferenceId auditReferenceId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ReportPublication(
            String id,
        String reportRunId,
        ReportPublicationStatus publicationStatus,
        String publishedByActorId,
        String publishedByDisplayNameSnapshot,
        Instant publishedAt,
        String publicationNote,
        String workflowReferenceId,
        String auditReferenceId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ReportPublication {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidReportingValueException("ReportPublication id must not be blank.");
        }
        // HRA-051 required: reportRunId
        if (reportRunId == null || reportRunId.isBlank()) {
            throw new InvalidReportingValueException("ReportPublication report run id must not be blank.");
        }
        // HRA-051 required: publicationStatus
        if (publicationStatus == null) {
            throw new InvalidReportingValueException("ReportPublication publication status must not be null.");
        }

        id = normalize(id);
        reportRunId = normalize(reportRunId);
        publishedByActorId = normalize(publishedByActorId);
        publishedByDisplayNameSnapshot = normalize(publishedByDisplayNameSnapshot);
        publicationNote = normalize(publicationNote);
        workflowReferenceId = normalize(workflowReferenceId);
        auditReferenceId = normalize(auditReferenceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
