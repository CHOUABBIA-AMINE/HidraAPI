/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskScore
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidRiskValueException("RiskScore id must not be blank.");
        }
        // HRA-051 required: riskAssessmentId
        if (riskAssessmentId == null || riskAssessmentId.isBlank()) {
            throw new InvalidRiskValueException("RiskScore risk assessment id must not be blank.");
        }
        // HRA-051 required: scoreType
        if (scoreType == null) {
            throw new InvalidRiskValueException("RiskScore score type must not be null.");
        }
        // HRA-051 required: riskMatrixId
        if (riskMatrixId == null || riskMatrixId.isBlank()) {
            throw new InvalidRiskValueException("RiskScore risk matrix id must not be blank.");
        }
        // HRA-051 required: likelihoodLevelId
        if (likelihoodLevelId == null || likelihoodLevelId.isBlank()) {
            throw new InvalidRiskValueException("RiskScore likelihood level id must not be blank.");
        }
        // HRA-051 required: consequenceLevelId
        if (consequenceLevelId == null || consequenceLevelId.isBlank()) {
            throw new InvalidRiskValueException("RiskScore consequence level id must not be blank.");
        }
        // HRA-051 required: scoreValue
        if (scoreValue == null) {
            throw new InvalidRiskValueException("RiskScore score value must not be null.");
        }
        // HRA-051 required: ratingId
        if (ratingId == null || ratingId.isBlank()) {
            throw new InvalidRiskValueException("RiskScore rating id must not be blank.");
        }
        // HRA-051 required: calculatedAt
        if (calculatedAt == null) {
            throw new InvalidRiskValueException("RiskScore calculated at must not be null.");
        }

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
