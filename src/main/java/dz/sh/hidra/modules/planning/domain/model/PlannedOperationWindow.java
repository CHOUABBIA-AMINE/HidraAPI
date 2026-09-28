/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlannedOperationWindow
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.model
 *
 * @Description : Planned operational event affecting capacity or operating mode.
 *
 */
package dz.sh.hidra.modules.planning.domain.model;

import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.planning.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Planned operational event affecting capacity or operating mode.
     *
         * @param id id
     * @param revisionId revisionId
     * @param scenarioId scenarioId
     * @param code code
     * @param windowTypeId windowTypeId
     * @param topologyAssetType topologyAssetType
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCode topologyAssetCode
     * @param plannedStart plannedStart
     * @param plannedEnd plannedEnd
     * @param capacityImpactPercent capacityImpactPercent
     * @param description description
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PlannedOperationWindow(
            String id,
        String revisionId,
        String scenarioId,
        String code,
        String windowTypeId,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        Instant plannedStart,
        Instant plannedEnd,
        BigDecimal capacityImpactPercent,
        String description,
        OperationWindowStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PlannedOperationWindow {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPlanningValueException("PlannedOperationWindow id must not be blank.");
        }
        // HRA-051 required: revisionId
        if (revisionId == null || revisionId.isBlank()) {
            throw new InvalidPlanningValueException("PlannedOperationWindow revision id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidPlanningValueException("PlannedOperationWindow code must not be blank.");
        }
        // HRA-051 required: windowTypeId
        if (windowTypeId == null || windowTypeId.isBlank()) {
            throw new InvalidPlanningValueException("PlannedOperationWindow window type id must not be blank.");
        }
        // HRA-051 required: topologyAssetId
        if (topologyAssetId == null || topologyAssetId.isBlank()) {
            throw new InvalidPlanningValueException("PlannedOperationWindow topology asset id must not be blank.");
        }
        // HRA-051 required: topologyAssetCode
        if (topologyAssetCode == null || topologyAssetCode.isBlank()) {
            throw new InvalidPlanningValueException("PlannedOperationWindow topology asset code must not be blank.");
        }
        // HRA-051 required: plannedStart
        if (plannedStart == null) {
            throw new InvalidPlanningValueException("PlannedOperationWindow planned start must not be null.");
        }
        // HRA-051 required: plannedEnd
        if (plannedEnd == null) {
            throw new InvalidPlanningValueException("PlannedOperationWindow planned end must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPlanningValueException("PlannedOperationWindow status must not be null.");
        }

        id = normalize(id);
        revisionId = normalize(revisionId);
        scenarioId = normalize(scenarioId);
        code = normalize(code);
        windowTypeId = normalize(windowTypeId);
        topologyAssetType = normalize(topologyAssetType);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCode = normalize(topologyAssetCode);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
