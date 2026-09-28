/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentEvidenceLink
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.model
 *
 * @Description : Link to upstream evidence or documents.
 *
 */
package dz.sh.hidra.modules.incident.domain.model;

import dz.sh.hidra.modules.incident.domain.exception.InvalidIncidentValueException;
import dz.sh.hidra.modules.incident.domain.value.*;
import java.time.Instant;

    /**
     * Link to upstream evidence or documents.
     *
         * @param id id
     * @param incidentId incidentId
     * @param evidenceType evidenceType
     * @param evidenceReferenceId evidenceReferenceId
     * @param evidenceReferenceCode evidenceReferenceCode
     * @param evidenceTitle evidenceTitle
     * @param evidenceSummary evidenceSummary
     * @param evidenceTimestamp evidenceTimestamp
     * @param attachedByActorId attachedByActorId
     * @param attachedAt attachedAt
     */
    public record IncidentEvidenceLink(
            String id,
        String incidentId,
        IncidentEvidenceType evidenceType,
        String evidenceReferenceId,
        String evidenceReferenceCode,
        String evidenceTitle,
        String evidenceSummary,
        Instant evidenceTimestamp,
        String attachedByActorId,
        Instant attachedAt
    ) {

        public IncidentEvidenceLink {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIncidentValueException("IncidentEvidenceLink id must not be blank.");
        }
        // HRA-051 required: incidentId
        if (incidentId == null || incidentId.isBlank()) {
            throw new InvalidIncidentValueException("IncidentEvidenceLink incident id must not be blank.");
        }
        // HRA-051 required: evidenceType
        if (evidenceType == null) {
            throw new InvalidIncidentValueException("IncidentEvidenceLink evidence type must not be null.");
        }
        // HRA-051 required: evidenceReferenceId
        if (evidenceReferenceId == null || evidenceReferenceId.isBlank()) {
            throw new InvalidIncidentValueException("IncidentEvidenceLink evidence reference id must not be blank.");
        }
        // HRA-051 required: attachedByActorId
        if (attachedByActorId == null || attachedByActorId.isBlank()) {
            throw new InvalidIncidentValueException("IncidentEvidenceLink attached by actor id must not be blank.");
        }
        // HRA-051 required: attachedAt
        if (attachedAt == null) {
            throw new InvalidIncidentValueException("IncidentEvidenceLink attached at must not be null.");
        }

        id = normalize(id);
        incidentId = normalize(incidentId);
        evidenceReferenceId = normalize(evidenceReferenceId);
        evidenceReferenceCode = normalize(evidenceReferenceCode);
        evidenceTitle = normalize(evidenceTitle);
        evidenceSummary = normalize(evidenceSummary);
        attachedByActorId = normalize(attachedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
