/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanConstraint
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.model
 *
 * @Description : Constraint affecting plan feasibility.
 *
 */
package dz.sh.hidra.modules.planning.domain.model;

import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.planning.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Constraint affecting plan feasibility.
     *
         * @param id id
     * @param revisionId revisionId
     * @param scenarioId scenarioId
     * @param constraintTypeId constraintTypeId
     * @param severity severity
     * @param topologyAssetType topologyAssetType
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCode topologyAssetCode
     * @param constraintValue constraintValue
     * @param unitId unitId
     * @param validFrom validFrom
     * @param validTo validTo
     * @param sourceModule sourceModule
     * @param sourceReferenceId sourceReferenceId
     * @param description description
     * @param blocking blocking
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PlanConstraint(
            String id,
        String revisionId,
        String scenarioId,
        String constraintTypeId,
        ConstraintSeverity severity,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        BigDecimal constraintValue,
        String unitId,
        Instant validFrom,
        Instant validTo,
        String sourceModule,
        String sourceReferenceId,
        String description,
        boolean blocking,
        ConstraintStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PlanConstraint {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPlanningValueException("PlanConstraint id must not be blank.");
        }
        // HRA-051 required: revisionId
        if (revisionId == null || revisionId.isBlank()) {
            throw new InvalidPlanningValueException("PlanConstraint revision id must not be blank.");
        }
        // HRA-051 required: constraintTypeId
        if (constraintTypeId == null || constraintTypeId.isBlank()) {
            throw new InvalidPlanningValueException("PlanConstraint constraint type id must not be blank.");
        }
        // HRA-051 required: severity
        if (severity == null) {
            throw new InvalidPlanningValueException("PlanConstraint severity must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPlanningValueException("PlanConstraint status must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidPlanningValueException("PlanConstraint valid to must not be before valid from.");
        }

        id = normalize(id);
        revisionId = normalize(revisionId);
        scenarioId = normalize(scenarioId);
        constraintTypeId = normalize(constraintTypeId);
        topologyAssetType = normalize(topologyAssetType);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCode = normalize(topologyAssetCode);
        unitId = normalize(unitId);
        sourceModule = normalize(sourceModule);
        sourceReferenceId = normalize(sourceReferenceId);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
