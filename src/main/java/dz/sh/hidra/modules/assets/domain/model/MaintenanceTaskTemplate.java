/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceTaskTemplate
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Reusable task template.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
import java.time.Instant;

    /**
     * Reusable task template.
     *
         * @param id id
     * @param templateCode templateCode
     * @param name name
     * @param assetTypeId assetTypeId
     * @param maintenanceStrategyId maintenanceStrategyId
     * @param taskTypeId taskTypeId
     * @param instructions instructions
     * @param estimatedDurationMinutes estimatedDurationMinutes
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record MaintenanceTaskTemplate(
            String id,
        String templateCode,
        String name,
        String assetTypeId,
        String maintenanceStrategyId,
        String taskTypeId,
        String instructions,
        Integer estimatedDurationMinutes,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public MaintenanceTaskTemplate {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("MaintenanceTaskTemplate id must not be blank.");
        }
        // HRA-051 required: templateCode
        if (templateCode == null || templateCode.isBlank()) {
            throw new InvalidAssetsValueException("MaintenanceTaskTemplate template code must not be blank.");
        }
        // HRA-051 required: taskTypeId
        if (taskTypeId == null || taskTypeId.isBlank()) {
            throw new InvalidAssetsValueException("MaintenanceTaskTemplate task type id must not be blank.");
        }

        id = normalize(id);
        templateCode = normalize(templateCode);
        name = normalize(name);
        assetTypeId = normalize(assetTypeId);
        maintenanceStrategyId = normalize(maintenanceStrategyId);
        taskTypeId = normalize(taskTypeId);
        instructions = normalize(instructions);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
