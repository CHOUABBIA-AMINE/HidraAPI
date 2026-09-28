/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceStrategy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Maintenance strategy.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
import dz.sh.hidra.modules.assets.domain.value.*;
import java.time.Instant;

    /**
     * Maintenance strategy.
     *
         * @param id id
     * @param strategyCode strategyCode
     * @param name name
     * @param strategyTypeId strategyTypeId
     * @param assetTypeId assetTypeId
     * @param description description
     * @param status status
     * @param effectiveFrom effectiveFrom
     * @param effectiveTo effectiveTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record MaintenanceStrategy(
            String id,
        String strategyCode,
        String name,
        String strategyTypeId,
        String assetTypeId,
        String description,
        MaintenanceStrategyStatus status,
        Instant effectiveFrom,
        Instant effectiveTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public MaintenanceStrategy {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("MaintenanceStrategy id must not be blank.");
        }
        // HRA-051 required: strategyCode
        if (strategyCode == null || strategyCode.isBlank()) {
            throw new InvalidAssetsValueException("MaintenanceStrategy strategy code must not be blank.");
        }
        // HRA-051 required: strategyTypeId
        if (strategyTypeId == null || strategyTypeId.isBlank()) {
            throw new InvalidAssetsValueException("MaintenanceStrategy strategy type id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidAssetsValueException("MaintenanceStrategy status must not be null.");
        }
        // HRA-051 order: effectiveFrom <= effectiveTo
        if (effectiveFrom != null && effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new InvalidAssetsValueException("MaintenanceStrategy effective to must not be before effective from.");
        }

        id = normalize(id);
        strategyCode = normalize(strategyCode);
        name = normalize(name);
        strategyTypeId = normalize(strategyTypeId);
        assetTypeId = normalize(assetTypeId);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
