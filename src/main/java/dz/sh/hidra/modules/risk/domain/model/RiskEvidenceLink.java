/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskEvidenceLink
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Evidence link from another module to a risk assessment.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import java.time.Instant;

    /**
     * Evidence link from another module to a risk assessment.
     *
         * @param id id
     * @param riskAssessmentId riskAssessmentId
     * @param evidenceModule evidenceModule
     * @param evidenceType evidenceType
     * @param evidenceId evidenceId
     * @param evidenceCodeSnapshot evidenceCodeSnapshot
     * @param evidenceLabelSnapshot evidenceLabelSnapshot
     * @param evidenceTimestamp evidenceTimestamp
     * @param evidenceHash evidenceHash
     * @param evidenceSummary evidenceSummary
     * @param createdAt createdAt
     */
    public record RiskEvidenceLink(
            String id,
        String riskAssessmentId,
        String evidenceModule,
        String evidenceType,
        String evidenceId,
        String evidenceCodeSnapshot,
        String evidenceLabelSnapshot,
        Instant evidenceTimestamp,
        String evidenceHash,
        String evidenceSummary,
        Instant createdAt
    ) {

        public RiskEvidenceLink {
        id = normalize(id);
        riskAssessmentId = normalize(riskAssessmentId);
        evidenceModule = normalize(evidenceModule);
        evidenceType = normalize(evidenceType);
        evidenceId = normalize(evidenceId);
        evidenceCodeSnapshot = normalize(evidenceCodeSnapshot);
        evidenceLabelSnapshot = normalize(evidenceLabelSnapshot);
        evidenceHash = normalize(evidenceHash);
        evidenceSummary = normalize(evidenceSummary);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
