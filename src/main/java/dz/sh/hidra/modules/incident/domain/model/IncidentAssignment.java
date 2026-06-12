/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentAssignment
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.model
 *
 * @Description : Responsible actor/unit assignment history.
 *
 */
package dz.sh.hidra.modules.incident.domain.model;

import java.time.Instant;

    /**
     * Responsible actor/unit assignment history.
     *
         * @param id id
     * @param incidentId incidentId
     * @param assignmentTypeId assignmentTypeId
     * @param assignedOrganizationUnitId assignedOrganizationUnitId
     * @param assignedOrganizationUnitNameSnapshot assignedOrganizationUnitNameSnapshot
     * @param assignedActorId assignedActorId
     * @param assignedActorNameSnapshot assignedActorNameSnapshot
     * @param assignedByActorId assignedByActorId
     * @param assignedAt assignedAt
     * @param acceptedAt acceptedAt
     * @param releasedAt releasedAt
     * @param releaseReason releaseReason
     * @param primaryAssignment primaryAssignment
     */
    public record IncidentAssignment(
            String id,
        String incidentId,
        String assignmentTypeId,
        String assignedOrganizationUnitId,
        String assignedOrganizationUnitNameSnapshot,
        String assignedActorId,
        String assignedActorNameSnapshot,
        String assignedByActorId,
        Instant assignedAt,
        Instant acceptedAt,
        Instant releasedAt,
        String releaseReason,
        boolean primaryAssignment
    ) {

        public IncidentAssignment {
        id = normalize(id);
        incidentId = normalize(incidentId);
        assignmentTypeId = normalize(assignmentTypeId);
        assignedOrganizationUnitId = normalize(assignedOrganizationUnitId);
        assignedOrganizationUnitNameSnapshot = normalize(assignedOrganizationUnitNameSnapshot);
        assignedActorId = normalize(assignedActorId);
        assignedActorNameSnapshot = normalize(assignedActorNameSnapshot);
        assignedByActorId = normalize(assignedByActorId);
        releaseReason = normalize(releaseReason);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
