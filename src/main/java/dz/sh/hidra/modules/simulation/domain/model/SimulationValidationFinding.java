/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationValidationFinding
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Validation issue found before or after a run.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import java.time.Instant;

    /**
     * Validation issue found before or after a run.
     *
         * @param id id
     * @param scenarioId scenarioId
     * @param runId runId
     * @param findingTypeId findingTypeId
     * @param severityId severityId
     * @param targetType targetType
     * @param targetId targetId
     * @param message message
     * @param resolved resolved
     * @param resolvedAt resolvedAt
     * @param createdAt createdAt
     */
    public record SimulationValidationFinding(
            String id,
        String scenarioId,
        String runId,
        String findingTypeId,
        String severityId,
        String targetType,
        String targetId,
        String message,
        boolean resolved,
        Instant resolvedAt,
        Instant createdAt
    ) {

        public SimulationValidationFinding {
        id = normalize(id);
        scenarioId = normalize(scenarioId);
        runId = normalize(runId);
        findingTypeId = normalize(findingTypeId);
        severityId = normalize(severityId);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        message = normalize(message);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
