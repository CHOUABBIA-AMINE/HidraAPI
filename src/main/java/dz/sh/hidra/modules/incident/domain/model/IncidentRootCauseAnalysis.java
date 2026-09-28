/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentRootCauseAnalysis
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.incident.domain.exception.InvalidIncidentValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIncidentValueException("IncidentRootCauseAnalysis id must not be blank.");
        }
        // HRA-051 required: incidentId
        if (incidentId == null || incidentId.isBlank()) {
            throw new InvalidIncidentValueException("IncidentRootCauseAnalysis incident id must not be blank.");
        }
        // HRA-051 required: rootCauseCategoryId
        if (rootCauseCategoryId == null || rootCauseCategoryId.isBlank()) {
            throw new InvalidIncidentValueException("IncidentRootCauseAnalysis root cause category id must not be blank.");
        }
        // HRA-051 required: performedByActorId
        if (performedByActorId == null || performedByActorId.isBlank()) {
            throw new InvalidIncidentValueException("IncidentRootCauseAnalysis performed by actor id must not be blank.");
        }
        // HRA-051 required: performedAt
        if (performedAt == null) {
            throw new InvalidIncidentValueException("IncidentRootCauseAnalysis performed at must not be null.");
        }

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
