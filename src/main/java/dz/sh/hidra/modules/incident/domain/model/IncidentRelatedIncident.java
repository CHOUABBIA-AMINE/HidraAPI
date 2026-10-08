/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentRelatedIncident
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.incident.domain.exception.InvalidIncidentValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIncidentValueException("IncidentRelatedIncident id must not be blank.");
        }
        // HRA-051 required: incidentId
        if (incidentId == null || incidentId.isBlank()) {
            throw new InvalidIncidentValueException("IncidentRelatedIncident incident id must not be blank.");
        }
        // HRA-051 required: relatedIncidentId
        if (relatedIncidentId == null || relatedIncidentId.isBlank()) {
            throw new InvalidIncidentValueException("IncidentRelatedIncident related incident id must not be blank.");
        }
        // HRA-051 required: relationshipTypeId
        if (relationshipTypeId == null || relationshipTypeId.isBlank()) {
            throw new InvalidIncidentValueException("IncidentRelatedIncident relationship type id must not be blank.");
        }
        // HRA-051 required: createdByActorId
        if (createdByActorId == null || createdByActorId.isBlank()) {
            throw new InvalidIncidentValueException("IncidentRelatedIncident created by actor id must not be blank.");
        }

        if(createdAt==null) throw new InvalidIncidentValueException("Relationship creation time is required.");
        if(incidentId.trim().equals(relatedIncidentId.trim())) throw new InvalidIncidentValueException("Incident cannot be related to itself.");
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
