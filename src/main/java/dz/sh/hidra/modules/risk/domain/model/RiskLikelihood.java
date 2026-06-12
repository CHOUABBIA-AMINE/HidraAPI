/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskLikelihood
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Likelihood, frequency, or probability assessment.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Likelihood, frequency, or probability assessment.
     *
         * @param id id
     * @param riskAssessmentId riskAssessmentId
     * @param likelihoodLevelId likelihoodLevelId
     * @param probabilityValue probabilityValue
     * @param frequencyEstimate frequencyEstimate
     * @param frequencyUnitId frequencyUnitId
     * @param likelihoodBasisId likelihoodBasisId
     * @param confidenceLevelId confidenceLevelId
     * @param evidenceSummary evidenceSummary
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record RiskLikelihood(
            String id,
        String riskAssessmentId,
        String likelihoodLevelId,
        BigDecimal probabilityValue,
        BigDecimal frequencyEstimate,
        String frequencyUnitId,
        String likelihoodBasisId,
        String confidenceLevelId,
        String evidenceSummary,
        Instant createdAt,
        Instant updatedAt
    ) {

        public RiskLikelihood {
        id = normalize(id);
        riskAssessmentId = normalize(riskAssessmentId);
        likelihoodLevelId = normalize(likelihoodLevelId);
        frequencyUnitId = normalize(frequencyUnitId);
        likelihoodBasisId = normalize(likelihoodBasisId);
        confidenceLevelId = normalize(confidenceLevelId);
        evidenceSummary = normalize(evidenceSummary);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
