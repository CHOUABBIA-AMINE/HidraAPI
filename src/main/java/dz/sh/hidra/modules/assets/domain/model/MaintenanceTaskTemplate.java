/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceTaskTemplate
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
