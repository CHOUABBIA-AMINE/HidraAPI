/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportPublication
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
