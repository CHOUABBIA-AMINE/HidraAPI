/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAcceptance
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Formal risk acceptance by accountable actor or committee.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import dz.sh.hidra.modules.risk.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Formal risk acceptance by accountable actor or committee.
     *
         * @param id id
     * @param riskAssessmentId riskAssessmentId
     * @param acceptanceNumber acceptanceNumber
     * @param acceptedRatingId acceptedRatingId
     * @param acceptedScore acceptedScore
     * @param acceptanceReasonId acceptanceReasonId
     * @param acceptanceJustification acceptanceJustification
     * @param acceptedByActorId acceptedByActorId
     * @param acceptedByDisplayNameSnapshot acceptedByDisplayNameSnapshot
     * @param acceptedByOrganizationUnitId acceptedByOrganizationUnitId
     * @param acceptedByOrganizationUnitNameSnapshot acceptedByOrganizationUnitNameSnapshot
     * @param acceptedAt acceptedAt
     * @param validUntil validUntil
     * @param reviewRequired reviewRequired
     * @param status status
     * @param workflowReferenceId workflowReferenceId
     * @param auditReferenceId auditReferenceId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record RiskAcceptance(
            String id,
        String riskAssessmentId,
        String acceptanceNumber,
        String acceptedRatingId,
        BigDecimal acceptedScore,
        String acceptanceReasonId,
        String acceptanceJustification,
        String acceptedByActorId,
        String acceptedByDisplayNameSnapshot,
        String acceptedByOrganizationUnitId,
        String acceptedByOrganizationUnitNameSnapshot,
        Instant acceptedAt,
        Instant validUntil,
        boolean reviewRequired,
        RiskAcceptanceStatus status,
        String workflowReferenceId,
        String auditReferenceId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public RiskAcceptance {
        id = normalize(id);
        riskAssessmentId = normalize(riskAssessmentId);
        acceptanceNumber = normalize(acceptanceNumber);
        acceptedRatingId = normalize(acceptedRatingId);
        acceptanceReasonId = normalize(acceptanceReasonId);
        acceptanceJustification = normalize(acceptanceJustification);
        acceptedByActorId = normalize(acceptedByActorId);
        acceptedByDisplayNameSnapshot = normalize(acceptedByDisplayNameSnapshot);
        acceptedByOrganizationUnitId = normalize(acceptedByOrganizationUnitId);
        acceptedByOrganizationUnitNameSnapshot = normalize(acceptedByOrganizationUnitNameSnapshot);
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
