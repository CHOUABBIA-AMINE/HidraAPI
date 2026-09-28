/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakEvidenceLink
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.model
 *
 * @Description : Evidence supporting or contradicting a leak candidate.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.model;

import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
import dz.sh.hidra.modules.leakdetection.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Evidence supporting or contradicting a leak candidate.
     *
         * @param id id
     * @param candidateId candidateId
     * @param evidenceType evidenceType
     * @param evidenceReferenceId evidenceReferenceId
     * @param evidenceCodeSnapshot evidenceCodeSnapshot
     * @param evidenceNameSnapshot evidenceNameSnapshot
     * @param evidenceDirection evidenceDirection
     * @param weight weight
     * @param description description
     * @param createdAt createdAt
     */
    public record LeakEvidenceLink(
            String id,
        String candidateId,
        LeakEvidenceType evidenceType,
        String evidenceReferenceId,
        String evidenceCodeSnapshot,
        String evidenceNameSnapshot,
        LeakEvidenceDirection evidenceDirection,
        BigDecimal weight,
        String description,
        Instant createdAt
    ) {

        public LeakEvidenceLink {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakEvidenceLink id must not be blank.");
        }
        // HRA-051 required: candidateId
        if (candidateId == null || candidateId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakEvidenceLink candidate id must not be blank.");
        }
        // HRA-051 required: evidenceType
        if (evidenceType == null) {
            throw new InvalidLeakDetectionValueException("LeakEvidenceLink evidence type must not be null.");
        }
        // HRA-051 required: evidenceReferenceId
        if (evidenceReferenceId == null || evidenceReferenceId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakEvidenceLink evidence reference id must not be blank.");
        }
        // HRA-051 required: evidenceDirection
        if (evidenceDirection == null) {
            throw new InvalidLeakDetectionValueException("LeakEvidenceLink evidence direction must not be null.");
        }

        id = normalize(id);
        candidateId = normalize(candidateId);
        evidenceReferenceId = normalize(evidenceReferenceId);
        evidenceCodeSnapshot = normalize(evidenceCodeSnapshot);
        evidenceNameSnapshot = normalize(evidenceNameSnapshot);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
