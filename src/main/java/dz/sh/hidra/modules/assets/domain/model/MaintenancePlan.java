/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenancePlan
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Planned maintenance structure.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.value.*;
import java.time.Instant;

    /**
     * Planned maintenance structure.
     *
         * @param id id
     * @param planCode planCode
     * @param name name
     * @param maintainableAssetId maintainableAssetId
     * @param maintenanceStrategyId maintenanceStrategyId
     * @param frequencyId frequencyId
     * @param nextDueAt nextDueAt
     * @param lastExecutedAt lastExecutedAt
     * @param status status
     * @param createdByActorId createdByActorId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record MaintenancePlan(
            String id,
        String planCode,
        String name,
        String maintainableAssetId,
        String maintenanceStrategyId,
        String frequencyId,
        Instant nextDueAt,
        Instant lastExecutedAt,
        MaintenancePlanStatus status,
        String createdByActorId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public MaintenancePlan {
        id = normalize(id);
        planCode = normalize(planCode);
        name = normalize(name);
        maintainableAssetId = normalize(maintainableAssetId);
        maintenanceStrategyId = normalize(maintenanceStrategyId);
        frequencyId = normalize(frequencyId);
        createdByActorId = normalize(createdByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
