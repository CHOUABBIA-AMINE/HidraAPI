/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskMatrixCell
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
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

import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidRiskValueException("RiskMatrixCell id must not be blank.");
        }
        // HRA-051 required: riskMatrixId
        if (riskMatrixId == null || riskMatrixId.isBlank()) {
            throw new InvalidRiskValueException("RiskMatrixCell risk matrix id must not be blank.");
        }
        // HRA-051 required: likelihoodLevelId
        if (likelihoodLevelId == null || likelihoodLevelId.isBlank()) {
            throw new InvalidRiskValueException("RiskMatrixCell likelihood level id must not be blank.");
        }
        // HRA-051 required: consequenceLevelId
        if (consequenceLevelId == null || consequenceLevelId.isBlank()) {
            throw new InvalidRiskValueException("RiskMatrixCell consequence level id must not be blank.");
        }
        // HRA-051 required: scoreValue
        if (scoreValue == null) {
            throw new InvalidRiskValueException("RiskMatrixCell score value must not be null.");
        }
        if (scoreValue.signum() < 0) {
            throw new InvalidRiskValueException(
                    "RiskMatrixCell score value must be non-negative."
            );
        }
        // HRA-051 required: ratingId
        if (ratingId == null || ratingId.isBlank()) {
            throw new InvalidRiskValueException("RiskMatrixCell rating id must not be blank.");
        }

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
