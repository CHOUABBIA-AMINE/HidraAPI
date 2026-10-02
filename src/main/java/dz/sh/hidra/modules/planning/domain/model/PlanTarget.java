/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanTarget
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.model
 *
 * @Description : Generic planned value for monitoring comparison.
 *
 */
package dz.sh.hidra.modules.planning.domain.model;

import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.planning.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Generic planned value for monitoring comparison.
     *
         * @param id id
     * @param revisionId revisionId
     * @param scenarioId scenarioId
     * @param nominationId nominationId
     * @param targetTypeId targetTypeId
     * @param topologyAssetType topologyAssetType
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCode topologyAssetCode
     * @param topologyAssetNameSnapshot topologyAssetNameSnapshot
     * @param telemetryPointId telemetryPointId
     * @param telemetryPointCodeSnapshot telemetryPointCodeSnapshot
     * @param targetValue targetValue
     * @param targetTextValue targetTextValue
     * @param unitId unitId
     * @param toleranceLow toleranceLow
     * @param toleranceHigh toleranceHigh
     * @param validFrom validFrom
     * @param validTo validTo
     * @param priority priority
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PlanTarget(
            String id,
        String revisionId,
        String scenarioId,
        String nominationId,
        String targetTypeId,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        String topologyAssetNameSnapshot,
        String telemetryPointId,
        String telemetryPointCodeSnapshot,
        BigDecimal targetValue,
        String targetTextValue,
        String unitId,
        BigDecimal toleranceLow,
        BigDecimal toleranceHigh,
        Instant validFrom,
        Instant validTo,
        Integer priority,
        PlanTargetStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PlanTarget {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPlanningValueException("PlanTarget id must not be blank.");
        }
        // HRA-051 required: revisionId
        if (revisionId == null || revisionId.isBlank()) {
            throw new InvalidPlanningValueException("PlanTarget revision id must not be blank.");
        }
        // HRA-051 required: targetTypeId
        if (targetTypeId == null || targetTypeId.isBlank()) {
            throw new InvalidPlanningValueException("PlanTarget target type id must not be blank.");
        }
        // HRA-051 required: topologyAssetId
        if (topologyAssetId == null || topologyAssetId.isBlank()) {
            throw new InvalidPlanningValueException("PlanTarget topology asset id must not be blank.");
        }
        // HRA-051 required: topologyAssetCode
        if (topologyAssetCode == null || topologyAssetCode.isBlank()) {
            throw new InvalidPlanningValueException("PlanTarget topology asset code must not be blank.");
        }
        // HRA-051 required: validFrom
        if (validFrom == null) {
            throw new InvalidPlanningValueException("PlanTarget valid from must not be null.");
        }
        // HRA-051 required: validTo
        if (validTo == null) {
            throw new InvalidPlanningValueException("PlanTarget valid to must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPlanningValueException("PlanTarget status must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidPlanningValueException("PlanTarget valid to must not be before valid from.");
        }

        id = normalize(id);
        revisionId = normalize(revisionId);
        scenarioId = normalize(scenarioId);
        nominationId = normalize(nominationId);
        targetTypeId = normalize(targetTypeId);
        topologyAssetType = normalize(topologyAssetType);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCode = normalize(topologyAssetCode);
        topologyAssetNameSnapshot = normalize(topologyAssetNameSnapshot);
        telemetryPointId = normalize(telemetryPointId);
        telemetryPointCodeSnapshot = normalize(telemetryPointCodeSnapshot);
        targetTextValue = normalize(targetTextValue);
        unitId = normalize(unitId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
