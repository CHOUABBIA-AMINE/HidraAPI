/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentRelatedIncident
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.model
 *
 * @Description : Relationship between incidents.
 *
 */
package dz.sh.hidra.modules.incident.domain.model;

import java.time.Instant;

    /**
     * Relationship between incidents.
     *
         * @param id id
     * @param incidentId incidentId
     * @param relatedIncidentId relatedIncidentId
     * @param relationshipTypeId relationshipTypeId
     * @param comment comment
     * @param createdByActorId createdByActorId
     * @param createdAt createdAt
     */
    public record IncidentRelatedIncident(
            String id,
        String incidentId,
        String relatedIncidentId,
        String relationshipTypeId,
        String comment,
        String createdByActorId,
        Instant createdAt
    ) {

        public IncidentRelatedIncident {
        id = normalize(id);
        incidentId = normalize(incidentId);
        relatedIncidentId = normalize(relatedIncidentId);
        relationshipTypeId = normalize(relationshipTypeId);
        comment = normalize(comment);
        createdByActorId = normalize(createdByActorId);
        }
        public boolean selfRelationship() {
            return incidentId != null && incidentId.equals(relatedIncidentId);
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
