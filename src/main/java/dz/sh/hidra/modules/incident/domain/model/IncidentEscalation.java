/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentEscalation
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.model
 *
 * @Description : Escalation record.
 *
 */
package dz.sh.hidra.modules.incident.domain.model;

import java.time.Instant;

    /**
     * Escalation record.
     *
         * @param id id
     * @param incidentId incidentId
     * @param fromLevel fromLevel
     * @param toLevel toLevel
     * @param reasonId reasonId
     * @param reasonComment reasonComment
     * @param escalatedToOrganizationUnitId escalatedToOrganizationUnitId
     * @param escalatedToActorId escalatedToActorId
     * @param escalatedByActorId escalatedByActorId
     * @param escalatedAt escalatedAt
     * @param acknowledgedAt acknowledgedAt
     */
    public record IncidentEscalation(
            String id,
        String incidentId,
        int fromLevel,
        int toLevel,
        String reasonId,
        String reasonComment,
        String escalatedToOrganizationUnitId,
        String escalatedToActorId,
        String escalatedByActorId,
        Instant escalatedAt,
        Instant acknowledgedAt
    ) {

        public IncidentEscalation {
        id = normalize(id);
        incidentId = normalize(incidentId);
        reasonId = normalize(reasonId);
        reasonComment = normalize(reasonComment);
        escalatedToOrganizationUnitId = normalize(escalatedToOrganizationUnitId);
        escalatedToActorId = normalize(escalatedToActorId);
        escalatedByActorId = normalize(escalatedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
