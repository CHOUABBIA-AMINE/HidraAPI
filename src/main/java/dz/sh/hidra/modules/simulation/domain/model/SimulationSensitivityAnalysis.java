/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationSensitivityAnalysis
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Comparison of parameter sensitivity runs.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Comparison of parameter sensitivity runs.
     *
         * @param id id
     * @param scenarioId scenarioId
     * @param baseRunId baseRunId
     * @param parameterCode parameterCode
     * @param parameterRangeText parameterRangeText
     * @param resultMetricCode resultMetricCode
     * @param sensitivityScore sensitivityScore
     * @param summaryText summaryText
     * @param createdAt createdAt
     */
    public record SimulationSensitivityAnalysis(
            String id,
        String scenarioId,
        String baseRunId,
        String parameterCode,
        String parameterRangeText,
        String resultMetricCode,
        BigDecimal sensitivityScore,
        String summaryText,
        Instant createdAt
    ) {

        public SimulationSensitivityAnalysis {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("SimulationSensitivityAnalysis id must not be blank.");
        }
        // HRA-051 required: scenarioId
        if (scenarioId == null || scenarioId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationSensitivityAnalysis scenario id must not be blank.");
        }
        // HRA-051 required: baseRunId
        if (baseRunId == null || baseRunId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationSensitivityAnalysis base run id must not be blank.");
        }
        // HRA-051 required: parameterCode
        if (parameterCode == null || parameterCode.isBlank()) {
            throw new InvalidSimulationValueException("SimulationSensitivityAnalysis parameter code must not be blank.");
        }
        // HRA-051 required: resultMetricCode
        if (resultMetricCode == null || resultMetricCode.isBlank()) {
            throw new InvalidSimulationValueException("SimulationSensitivityAnalysis result metric code must not be blank.");
        }

        id = normalize(id);
        scenarioId = normalize(scenarioId);
        baseRunId = normalize(baseRunId);
        parameterCode = normalize(parameterCode);
        parameterRangeText = normalize(parameterRangeText);
        resultMetricCode = normalize(resultMetricCode);
        summaryText = normalize(summaryText);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
