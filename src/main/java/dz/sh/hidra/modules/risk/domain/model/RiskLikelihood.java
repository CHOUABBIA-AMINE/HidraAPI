/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskLikelihood
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidRiskValueException("RiskLikelihood id must not be blank.");
        }
        // HRA-051 required: riskAssessmentId
        if (riskAssessmentId == null || riskAssessmentId.isBlank()) {
            throw new InvalidRiskValueException("RiskLikelihood risk assessment id must not be blank.");
        }
        // HRA-051 required: likelihoodLevelId
        if (likelihoodLevelId == null || likelihoodLevelId.isBlank()) {
            throw new InvalidRiskValueException("RiskLikelihood likelihood level id must not be blank.");
        }

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
