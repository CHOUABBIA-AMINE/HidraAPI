/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentEvidenceLink
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
