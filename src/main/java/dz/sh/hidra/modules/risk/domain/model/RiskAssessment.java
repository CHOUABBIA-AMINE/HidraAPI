/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Formal evaluation of one risk scenario over a defined scope.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;
import dz.sh.hidra.modules.risk.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Formal evaluation of one risk scenario over a defined scope.
     *
         * @param id id
     * @param riskRegisterId riskRegisterId
     * @param assessmentNumber assessmentNumber
     * @param title title
     * @param description description
     * @param assessmentTypeId assessmentTypeId
     * @param methodologyId methodologyId
     * @param scopeId scopeId
     * @param riskScenarioId riskScenarioId
     * @param status status
     * @param assessmentDate assessmentDate
     * @param validFrom validFrom
     * @param validTo validTo
     * @param assessedByActorId assessedByActorId
     * @param assessedByDisplayNameSnapshot assessedByDisplayNameSnapshot
     * @param reviewedByActorId reviewedByActorId
     * @param reviewedByDisplayNameSnapshot reviewedByDisplayNameSnapshot
     * @param approvedByActorId approvedByActorId
     * @param approvedByDisplayNameSnapshot approvedByDisplayNameSnapshot
     * @param approvedAt approvedAt
     * @param inherentLikelihoodId inherentLikelihoodId
     * @param inherentConsequenceId inherentConsequenceId
     * @param inherentScore inherentScore
     * @param inherentRatingId inherentRatingId
     * @param residualLikelihoodId residualLikelihoodId
     * @param residualConsequenceId residualConsequenceId
     * @param residualScore residualScore
     * @param residualRatingId residualRatingId
     * @param confidenceLevelId confidenceLevelId
     * @param uncertaintyNote uncertaintyNote
     * @param workflowReferenceId workflowReferenceId
     * @param auditReferenceId auditReferenceId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record RiskAssessment(
            String id,
        String riskRegisterId,
        String assessmentNumber,
        String title,
        String description,
        String assessmentTypeId,
        String methodologyId,
        String scopeId,
        String riskScenarioId,
        RiskAssessmentStatus status,
        Instant assessmentDate,
        Instant validFrom,
        Instant validTo,
        String assessedByActorId,
        String assessedByDisplayNameSnapshot,
        String reviewedByActorId,
        String reviewedByDisplayNameSnapshot,
        String approvedByActorId,
        String approvedByDisplayNameSnapshot,
        Instant approvedAt,
        String inherentLikelihoodId,
        String inherentConsequenceId,
        BigDecimal inherentScore,
        String inherentRatingId,
        String residualLikelihoodId,
        String residualConsequenceId,
        BigDecimal residualScore,
        String residualRatingId,
        String confidenceLevelId,
        String uncertaintyNote,
        String workflowReferenceId,
        String auditReferenceId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public RiskAssessment {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidRiskValueException("RiskAssessment id must not be blank.");
        }
        // HRA-051 required: riskRegisterId
        if (riskRegisterId == null || riskRegisterId.isBlank()) {
            throw new InvalidRiskValueException("RiskAssessment risk register id must not be blank.");
        }
        // HRA-051 required: assessmentNumber
        if (assessmentNumber == null || assessmentNumber.isBlank()) {
            throw new InvalidRiskValueException("RiskAssessment assessment number must not be blank.");
        }
        // HRA-051 required: assessmentTypeId
        if (assessmentTypeId == null || assessmentTypeId.isBlank()) {
            throw new InvalidRiskValueException("RiskAssessment assessment type id must not be blank.");
        }
        // HRA-051 required: methodologyId
        if (methodologyId == null || methodologyId.isBlank()) {
            throw new InvalidRiskValueException("RiskAssessment methodology id must not be blank.");
        }
        // HRA-051 required: riskScenarioId
        if (riskScenarioId == null || riskScenarioId.isBlank()) {
            throw new InvalidRiskValueException("RiskAssessment risk scenario id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidRiskValueException("RiskAssessment status must not be null.");
        }
        // HRA-051 required: assessmentDate
        if (assessmentDate == null) {
            throw new InvalidRiskValueException("RiskAssessment assessment date must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidRiskValueException("RiskAssessment valid to must not be before valid from.");
        }

        id = normalize(id);
        riskRegisterId = normalize(riskRegisterId);
        assessmentNumber = normalize(assessmentNumber);
        title = normalize(title);
        description = normalize(description);
        assessmentTypeId = normalize(assessmentTypeId);
        methodologyId = normalize(methodologyId);
        scopeId = normalize(scopeId);
        riskScenarioId = normalize(riskScenarioId);
        assessedByActorId = normalize(assessedByActorId);
        assessedByDisplayNameSnapshot = normalize(assessedByDisplayNameSnapshot);
        reviewedByActorId = normalize(reviewedByActorId);
        reviewedByDisplayNameSnapshot = normalize(reviewedByDisplayNameSnapshot);
        approvedByActorId = normalize(approvedByActorId);
        approvedByDisplayNameSnapshot = normalize(approvedByDisplayNameSnapshot);
        inherentLikelihoodId = normalize(inherentLikelihoodId);
        inherentConsequenceId = normalize(inherentConsequenceId);
        inherentRatingId = normalize(inherentRatingId);
        residualLikelihoodId = normalize(residualLikelihoodId);
        residualConsequenceId = normalize(residualConsequenceId);
        residualRatingId = normalize(residualRatingId);
        confidenceLevelId = normalize(confidenceLevelId);
        uncertaintyNote = normalize(uncertaintyNote);
        workflowReferenceId = normalize(workflowReferenceId);
        auditReferenceId = normalize(auditReferenceId);
        }
        public boolean approved() {
            return status == RiskAssessmentStatus.APPROVED
                    || status == RiskAssessmentStatus.ACTIVE;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
