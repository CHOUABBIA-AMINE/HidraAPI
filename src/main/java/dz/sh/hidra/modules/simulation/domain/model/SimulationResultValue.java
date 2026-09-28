/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationResultValue
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Point result for a simulated target.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Point result for a simulated target.
     *
         * @param id id
     * @param runId runId
     * @param targetType targetType
     * @param targetId targetId
     * @param metricCode metricCode
     * @param value value
     * @param unitCode unitCode
     * @param timeOffsetSeconds timeOffsetSeconds
     * @param recordedAt recordedAt
     */
    public record SimulationResultValue(
            String id,
        String runId,
        String targetType,
        String targetId,
        String metricCode,
        BigDecimal value,
        String unitCode,
        Long timeOffsetSeconds,
        Instant recordedAt
    ) {

        public SimulationResultValue {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("SimulationResultValue id must not be blank.");
        }
        // HRA-051 required: runId
        if (runId == null || runId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationResultValue run id must not be blank.");
        }
        // HRA-051 required: targetId
        if (targetId == null || targetId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationResultValue target id must not be blank.");
        }
        // HRA-051 required: metricCode
        if (metricCode == null || metricCode.isBlank()) {
            throw new InvalidSimulationValueException("SimulationResultValue metric code must not be blank.");
        }
        // HRA-051 required: value
        if (value == null) {
            throw new InvalidSimulationValueException("SimulationResultValue value must not be null.");
        }
        // HRA-051 required: recordedAt
        if (recordedAt == null) {
            throw new InvalidSimulationValueException("SimulationResultValue recorded at must not be null.");
        }

        id = normalize(id);
        runId = normalize(runId);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        metricCode = normalize(metricCode);
        unitCode = normalize(unitCode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
