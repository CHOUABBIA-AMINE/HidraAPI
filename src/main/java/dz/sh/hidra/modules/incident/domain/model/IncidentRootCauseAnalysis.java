/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentRootCauseAnalysis
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.model
 *
 * @Description : Root cause analysis result.
 *
 */
package dz.sh.hidra.modules.incident.domain.model;

import java.time.Instant;

    /**
     * Root cause analysis result.
     *
         * @param id id
     * @param incidentId incidentId
     * @param rootCauseCategoryId rootCauseCategoryId
     * @param rootCauseCodeId rootCauseCodeId
     * @param methodId methodId
     * @param summary summary
     * @param analysisDetails analysisDetails
     * @param contributingFactors contributingFactors
     * @param confidenceLevelId confidenceLevelId
     * @param performedByActorId performedByActorId
     * @param performedAt performedAt
     * @param approvedByActorId approvedByActorId
     * @param approvedAt approvedAt
     */
    public record IncidentRootCauseAnalysis(
            String id,
        String incidentId,
        String rootCauseCategoryId,
        String rootCauseCodeId,
        String methodId,
        String summary,
        String analysisDetails,
        String contributingFactors,
        String confidenceLevelId,
        String performedByActorId,
        Instant performedAt,
        String approvedByActorId,
        Instant approvedAt
    ) {

        public IncidentRootCauseAnalysis {
        id = normalize(id);
        incidentId = normalize(incidentId);
        rootCauseCategoryId = normalize(rootCauseCategoryId);
        rootCauseCodeId = normalize(rootCauseCodeId);
        methodId = normalize(methodId);
        summary = normalize(summary);
        analysisDetails = normalize(analysisDetails);
        contributingFactors = normalize(contributingFactors);
        confidenceLevelId = normalize(confidenceLevelId);
        performedByActorId = normalize(performedByActorId);
        approvedByActorId = normalize(approvedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
