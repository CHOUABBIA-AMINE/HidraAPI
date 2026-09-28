/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationValidationFinding
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("SimulationValidationFinding id must not be blank.");
        }
        // HRA-051 required: findingTypeId
        if (findingTypeId == null || findingTypeId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationValidationFinding finding type id must not be blank.");
        }
        // HRA-051 required: severityId
        if (severityId == null || severityId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationValidationFinding severity id must not be blank.");
        }

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
