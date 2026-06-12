/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceStrategy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
