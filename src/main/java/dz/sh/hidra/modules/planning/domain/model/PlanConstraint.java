/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanConstraint
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
