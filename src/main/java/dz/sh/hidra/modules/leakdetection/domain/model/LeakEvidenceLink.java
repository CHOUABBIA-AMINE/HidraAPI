/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakEvidenceLink
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
