/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentResponseAction
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.model
 *
 * @Description : Action taken during incident response.
 *
 */
package dz.sh.hidra.modules.incident.domain.model;

import dz.sh.hidra.modules.incident.domain.exception.InvalidIncidentValueException;
import dz.sh.hidra.modules.incident.domain.value.*;
import java.time.Instant;

    /**
     * Action taken during incident response.
     *
         * @param id id
     * @param incidentId incidentId
     * @param actionTypeId actionTypeId
     * @param actionStatus actionStatus
     * @param description description
     * @param targetType targetType
     * @param targetReferenceId targetReferenceId
     * @param targetReferenceCode targetReferenceCode
     * @param plannedStartAt plannedStartAt
     * @param plannedEndAt plannedEndAt
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param performedByActorId performedByActorId
     * @param performedByActorNameSnapshot performedByActorNameSnapshot
     * @param organizationUnitId organizationUnitId
     * @param resultSummary resultSummary
     * @param failureReason failureReason
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record IncidentResponseAction(
            String id,
        String incidentId,
        String actionTypeId,
        ResponseActionStatus actionStatus,
        String description,
        ResponseTargetType targetType,
        String targetReferenceId,
        String targetReferenceCode,
        Instant plannedStartAt,
        Instant plannedEndAt,
        Instant startedAt,
        Instant completedAt,
        String performedByActorId,
        String performedByActorNameSnapshot,
        String organizationUnitId,
        String resultSummary,
        String failureReason,
        Instant createdAt,
        Instant updatedAt
    ) {

        public IncidentResponseAction {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIncidentValueException("IncidentResponseAction id must not be blank.");
        }
        // HRA-051 required: incidentId
        if (incidentId == null || incidentId.isBlank()) {
            throw new InvalidIncidentValueException("IncidentResponseAction incident id must not be blank.");
        }
        // HRA-051 required: actionTypeId
        if (actionTypeId == null || actionTypeId.isBlank()) {
            throw new InvalidIncidentValueException("IncidentResponseAction action type id must not be blank.");
        }
        // HRA-051 required: actionStatus
        if (actionStatus == null) {
            throw new InvalidIncidentValueException("IncidentResponseAction action status must not be null.");
        }

        id = normalize(id);
        incidentId = normalize(incidentId);
        actionTypeId = normalize(actionTypeId);
        description = normalize(description);
        targetReferenceId = normalize(targetReferenceId);
        targetReferenceCode = normalize(targetReferenceCode);
        performedByActorId = normalize(performedByActorId);
        performedByActorNameSnapshot = normalize(performedByActorNameSnapshot);
        organizationUnitId = normalize(organizationUnitId);
        resultSummary = normalize(resultSummary);
        failureReason = normalize(failureReason);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
