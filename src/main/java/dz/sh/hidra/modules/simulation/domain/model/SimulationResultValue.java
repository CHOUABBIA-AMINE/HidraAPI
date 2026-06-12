/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationResultValue
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
