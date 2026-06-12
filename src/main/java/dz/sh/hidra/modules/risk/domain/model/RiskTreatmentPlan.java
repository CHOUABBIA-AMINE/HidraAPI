/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskTreatmentPlan
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Formal plan to reduce, transfer, avoid, monitor, or accept risk.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import dz.sh.hidra.modules.risk.domain.value.*;
import java.time.Instant;

    /**
     * Formal plan to reduce, transfer, avoid, monitor, or accept risk.
     *
         * @param id id
     * @param riskAssessmentId riskAssessmentId
     * @param treatmentStrategyId treatmentStrategyId
     * @param title title
     * @param description description
     * @param ownerOrganizationUnitId ownerOrganizationUnitId
     * @param ownerOrganizationUnitNameSnapshot ownerOrganizationUnitNameSnapshot
     * @param ownerActorId ownerActorId
     * @param ownerDisplayNameSnapshot ownerDisplayNameSnapshot
     * @param status status
     * @param targetResidualRatingId targetResidualRatingId
     * @param targetCompletionDate targetCompletionDate
     * @param workflowReferenceId workflowReferenceId
     * @param auditReferenceId auditReferenceId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record RiskTreatmentPlan(
            String id,
        String riskAssessmentId,
        String treatmentStrategyId,
        String title,
        String description,
        String ownerOrganizationUnitId,
        String ownerOrganizationUnitNameSnapshot,
        String ownerActorId,
        String ownerDisplayNameSnapshot,
        RiskTreatmentStatus status,
        String targetResidualRatingId,
        Instant targetCompletionDate,
        String workflowReferenceId,
        String auditReferenceId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public RiskTreatmentPlan {
        id = normalize(id);
        riskAssessmentId = normalize(riskAssessmentId);
        treatmentStrategyId = normalize(treatmentStrategyId);
        title = normalize(title);
        description = normalize(description);
        ownerOrganizationUnitId = normalize(ownerOrganizationUnitId);
        ownerOrganizationUnitNameSnapshot = normalize(ownerOrganizationUnitNameSnapshot);
        ownerActorId = normalize(ownerActorId);
        ownerDisplayNameSnapshot = normalize(ownerDisplayNameSnapshot);
        targetResidualRatingId = normalize(targetResidualRatingId);
        workflowReferenceId = normalize(workflowReferenceId);
        auditReferenceId = normalize(auditReferenceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
