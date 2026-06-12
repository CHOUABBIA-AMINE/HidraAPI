/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResidualRiskAssessment
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Reassessment after controls and treatments.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Reassessment after controls and treatments.
     *
         * @param id id
     * @param riskAssessmentId riskAssessmentId
     * @param treatmentPlanId treatmentPlanId
     * @param reassessmentDate reassessmentDate
     * @param residualLikelihoodId residualLikelihoodId
     * @param residualConsequenceId residualConsequenceId
     * @param residualScore residualScore
     * @param residualRatingId residualRatingId
     * @param residualConfidenceLevelId residualConfidenceLevelId
     * @param residualJustification residualJustification
     * @param assessedByActorId assessedByActorId
     * @param approvedByActorId approvedByActorId
     * @param approvedAt approvedAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ResidualRiskAssessment(
            String id,
        String riskAssessmentId,
        String treatmentPlanId,
        Instant reassessmentDate,
        String residualLikelihoodId,
        String residualConsequenceId,
        BigDecimal residualScore,
        String residualRatingId,
        String residualConfidenceLevelId,
        String residualJustification,
        String assessedByActorId,
        String approvedByActorId,
        Instant approvedAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ResidualRiskAssessment {
        id = normalize(id);
        riskAssessmentId = normalize(riskAssessmentId);
        treatmentPlanId = normalize(treatmentPlanId);
        residualLikelihoodId = normalize(residualLikelihoodId);
        residualConsequenceId = normalize(residualConsequenceId);
        residualRatingId = normalize(residualRatingId);
        residualConfidenceLevelId = normalize(residualConfidenceLevelId);
        residualJustification = normalize(residualJustification);
        assessedByActorId = normalize(assessedByActorId);
        approvedByActorId = normalize(approvedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
