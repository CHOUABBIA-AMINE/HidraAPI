/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentClosure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.model
 *
 * @Description : Formal closure decision and evidence confirmation.
 *
 */
package dz.sh.hidra.modules.incident.domain.model;

import dz.sh.hidra.modules.incident.domain.exception.InvalidIncidentValueException;
import java.time.Instant;

    /**
     * Formal closure decision and evidence confirmation.
     *
         * @param id id
     * @param incidentId incidentId
     * @param closureSummary closureSummary
     * @param resolutionVerified resolutionVerified
     * @param evidenceReviewed evidenceReviewed
     * @param rootCauseReviewed rootCauseReviewed
     * @param followUpActionsCreated followUpActionsCreated
     * @param closedByActorId closedByActorId
     * @param closedByActorNameSnapshot closedByActorNameSnapshot
     * @param closedAt closedAt
     * @param workflowInstanceId workflowInstanceId
     */
    public record IncidentClosure(
            String id,
        String incidentId,
        String closureSummary,
        boolean resolutionVerified,
        boolean evidenceReviewed,
        boolean rootCauseReviewed,
        boolean followUpActionsCreated,
        String closedByActorId,
        String closedByActorNameSnapshot,
        Instant closedAt,
        String workflowInstanceId
    ) {

        public IncidentClosure {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIncidentValueException("IncidentClosure id must not be blank.");
        }
        // HRA-051 required: incidentId
        if (incidentId == null || incidentId.isBlank()) {
            throw new InvalidIncidentValueException("IncidentClosure incident id must not be blank.");
        }
        // HRA-051 required: closedByActorId
        if (closedByActorId == null || closedByActorId.isBlank()) {
            throw new InvalidIncidentValueException("IncidentClosure closed by actor id must not be blank.");
        }
        // HRA-051 required: closedAt
        if (closedAt == null) {
            throw new InvalidIncidentValueException("IncidentClosure closed at must not be null.");
        }

        id = normalize(id);
        incidentId = normalize(incidentId);
        closureSummary = normalize(closureSummary);
        closedByActorId = normalize(closedByActorId);
        closedByActorNameSnapshot = normalize(closedByActorNameSnapshot);
        workflowInstanceId = normalize(workflowInstanceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
