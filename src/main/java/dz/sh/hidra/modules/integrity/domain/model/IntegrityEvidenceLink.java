/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityEvidenceLink
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrityValueException("IntegrityEvidenceLink id must not be blank.");
        }
        // HRA-051 required: targetId
        if (targetId == null || targetId.isBlank()) {
            throw new InvalidIntegrityValueException("IntegrityEvidenceLink target id must not be blank.");
        }
        // HRA-051 required: evidenceType
        if (evidenceType == null) {
            throw new InvalidIntegrityValueException("IntegrityEvidenceLink evidence type must not be null.");
        }
        // HRA-051 required: evidenceReferenceId
        if (evidenceReferenceId == null || evidenceReferenceId.isBlank()) {
            throw new InvalidIntegrityValueException("IntegrityEvidenceLink evidence reference id must not be blank.");
        }
        // HRA-051 required: attachedAt
        if (attachedAt == null) {
            throw new InvalidIntegrityValueException("IntegrityEvidenceLink attached at must not be null.");
        }

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
