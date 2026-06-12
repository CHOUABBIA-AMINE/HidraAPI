/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseEvidenceLink
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 *
 * @Description : HSE case evidence link.
 *
 */
package dz.sh.hidra.modules.hse.domain.model;

import dz.sh.hidra.modules.hse.domain.value.*;
import java.time.Instant;

    /**
     * HSE case evidence link.
     *
         * @param id id
     * @param hseCaseId hseCaseId
     * @param evidenceType evidenceType
     * @param evidenceReferenceId evidenceReferenceId
     * @param evidenceCodeSnapshot evidenceCodeSnapshot
     * @param evidenceLabelSnapshot evidenceLabelSnapshot
     * @param evidenceSummary evidenceSummary
     * @param evidenceTimestamp evidenceTimestamp
     * @param attachedByActorId attachedByActorId
     * @param attachedAt attachedAt
     */
    public record HseCaseEvidenceLink(
            String id,
        String hseCaseId,
        HseEvidenceType evidenceType,
        String evidenceReferenceId,
        String evidenceCodeSnapshot,
        String evidenceLabelSnapshot,
        String evidenceSummary,
        Instant evidenceTimestamp,
        String attachedByActorId,
        Instant attachedAt
    ) {

        public HseCaseEvidenceLink {
        id = normalize(id);
        hseCaseId = normalize(hseCaseId);
        evidenceReferenceId = normalize(evidenceReferenceId);
        evidenceCodeSnapshot = normalize(evidenceCodeSnapshot);
        evidenceLabelSnapshot = normalize(evidenceLabelSnapshot);
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
