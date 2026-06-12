/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskMatrixCell
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Risk score/rating for a likelihood and consequence pair.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Risk score/rating for a likelihood and consequence pair.
     *
         * @param id id
     * @param riskMatrixId riskMatrixId
     * @param likelihoodLevelId likelihoodLevelId
     * @param consequenceLevelId consequenceLevelId
     * @param scoreValue scoreValue
     * @param ratingId ratingId
     * @param colorCode colorCode
     * @param requiresTreatment requiresTreatment
     * @param requiresApproval requiresApproval
     * @param requiresExecutiveAcceptance requiresExecutiveAcceptance
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record RiskMatrixCell(
            String id,
        String riskMatrixId,
        String likelihoodLevelId,
        String consequenceLevelId,
        BigDecimal scoreValue,
        String ratingId,
        String colorCode,
        boolean requiresTreatment,
        boolean requiresApproval,
        boolean requiresExecutiveAcceptance,
        Instant createdAt,
        Instant updatedAt
    ) {

        public RiskMatrixCell {
        id = normalize(id);
        riskMatrixId = normalize(riskMatrixId);
        likelihoodLevelId = normalize(likelihoodLevelId);
        consequenceLevelId = normalize(consequenceLevelId);
        ratingId = normalize(ratingId);
        colorCode = normalize(colorCode);
        }
        public boolean treatmentOrApprovalRequired() {
            return requiresTreatment || requiresApproval || requiresExecutiveAcceptance;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
