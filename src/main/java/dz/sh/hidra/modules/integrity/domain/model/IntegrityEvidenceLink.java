/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityEvidenceLink
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : Evidence link.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import dz.sh.hidra.modules.integrity.domain.value.*;
import java.time.Instant;

    /**
     * Evidence link.
     *
         * @param id id
     * @param targetType targetType
     * @param targetId targetId
     * @param evidenceType evidenceType
     * @param evidenceReferenceId evidenceReferenceId
     * @param evidenceCodeSnapshot evidenceCodeSnapshot
     * @param evidenceLabelSnapshot evidenceLabelSnapshot
     * @param description description
     * @param evidenceTimestamp evidenceTimestamp
     * @param attachedByActorId attachedByActorId
     * @param attachedAt attachedAt
     */
    public record IntegrityEvidenceLink(
            String id,
        String targetType,
        String targetId,
        EvidenceType evidenceType,
        String evidenceReferenceId,
        String evidenceCodeSnapshot,
        String evidenceLabelSnapshot,
        String description,
        Instant evidenceTimestamp,
        String attachedByActorId,
        Instant attachedAt
    ) {

        public IntegrityEvidenceLink {
        id = normalize(id);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        evidenceReferenceId = normalize(evidenceReferenceId);
        evidenceCodeSnapshot = normalize(evidenceCodeSnapshot);
        evidenceLabelSnapshot = normalize(evidenceLabelSnapshot);
        description = normalize(description);
        attachedByActorId = normalize(attachedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
