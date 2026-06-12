/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskScore
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Calculated score details for an assessment.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import dz.sh.hidra.modules.risk.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Calculated score details for an assessment.
     *
         * @param id id
     * @param riskAssessmentId riskAssessmentId
     * @param scoreType scoreType
     * @param riskMatrixId riskMatrixId
     * @param likelihoodLevelId likelihoodLevelId
     * @param consequenceLevelId consequenceLevelId
     * @param scoreValue scoreValue
     * @param ratingId ratingId
     * @param ratingLabelSnapshot ratingLabelSnapshot
     * @param calculatedAt calculatedAt
     * @param calculationMethod calculationMethod
     * @param explanation explanation
     * @param createdAt createdAt
     */
    public record RiskScore(
            String id,
        String riskAssessmentId,
        RiskScoreType scoreType,
        String riskMatrixId,
        String likelihoodLevelId,
        String consequenceLevelId,
        BigDecimal scoreValue,
        String ratingId,
        String ratingLabelSnapshot,
        Instant calculatedAt,
        String calculationMethod,
        String explanation,
        Instant createdAt
    ) {

        public RiskScore {
        id = normalize(id);
        riskAssessmentId = normalize(riskAssessmentId);
        riskMatrixId = normalize(riskMatrixId);
        likelihoodLevelId = normalize(likelihoodLevelId);
        consequenceLevelId = normalize(consequenceLevelId);
        ratingId = normalize(ratingId);
        ratingLabelSnapshot = normalize(ratingLabelSnapshot);
        calculationMethod = normalize(calculationMethod);
        explanation = normalize(explanation);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
