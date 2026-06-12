/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanTarget
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
