/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskConsequence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Consequence categories and scored impacts.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Consequence categories and scored impacts.
     *
         * @param id id
     * @param riskAssessmentId riskAssessmentId
     * @param categoryId categoryId
     * @param consequenceLevelId consequenceLevelId
     * @param description description
     * @param peopleImpactLevelId peopleImpactLevelId
     * @param environmentImpactLevelId environmentImpactLevelId
     * @param productionImpactLevelId productionImpactLevelId
     * @param assetImpactLevelId assetImpactLevelId
     * @param financialImpactLevelId financialImpactLevelId
     * @param reputationImpactLevelId reputationImpactLevelId
     * @param complianceImpactLevelId complianceImpactLevelId
     * @param estimatedCost estimatedCost
     * @param currencyCode currencyCode
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record RiskConsequence(
            String id,
        String riskAssessmentId,
        String categoryId,
        String consequenceLevelId,
        String description,
        String peopleImpactLevelId,
        String environmentImpactLevelId,
        String productionImpactLevelId,
        String assetImpactLevelId,
        String financialImpactLevelId,
        String reputationImpactLevelId,
        String complianceImpactLevelId,
        BigDecimal estimatedCost,
        String currencyCode,
        Instant createdAt,
        Instant updatedAt
    ) {

        public RiskConsequence {
        id = normalize(id);
        riskAssessmentId = normalize(riskAssessmentId);
        categoryId = normalize(categoryId);
        consequenceLevelId = normalize(consequenceLevelId);
        description = normalize(description);
        peopleImpactLevelId = normalize(peopleImpactLevelId);
        environmentImpactLevelId = normalize(environmentImpactLevelId);
        productionImpactLevelId = normalize(productionImpactLevelId);
        assetImpactLevelId = normalize(assetImpactLevelId);
        financialImpactLevelId = normalize(financialImpactLevelId);
        reputationImpactLevelId = normalize(reputationImpactLevelId);
        complianceImpactLevelId = normalize(complianceImpactLevelId);
        currencyCode = normalize(currencyCode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
