/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEquipmentInput
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Captures typed advisory model configuration with explicit equipment endpoints.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.math.BigDecimal;

/** Selected model configuration, not measured running state or an actuator instruction. */
public record SimulationEquipmentInput(
        String id,
        String fromNodeId,
        String toNodeId,
        Kind kind,
        CompressorConfiguration compressor,
        ValveConfiguration valve
) {
    public SimulationEquipmentInput {
        id = required(id, "Equipment identity");
        fromNodeId = required(fromNodeId, "From-node identity");
        toNodeId = required(toNodeId, "To-node identity");
        if (fromNodeId.equals(toNodeId)) {
            throw new InvalidSimulationValueException("Equipment endpoints must be distinct.");
        }
        if (kind == null
                || (kind == Kind.COMPRESSOR && (compressor == null || valve != null))
                || (kind == Kind.VALVE && (valve == null || compressor != null))) {
            throw new InvalidSimulationValueException("Equipment requires exactly its kind's configuration.");
        }
    }

    public enum Kind {
        COMPRESSOR, VALVE
    }

    public record CompressorConfiguration(String curveId, String curveRevisionId,
            BigDecimal configuredSpeedRevolutionsPerMinute) {
        public CompressorConfiguration {
            curveId = required(curveId, "Curve identity");
            curveRevisionId = required(curveRevisionId, "Curve revision identity");
            if (configuredSpeedRevolutionsPerMinute == null || configuredSpeedRevolutionsPerMinute.signum() <= 0) {
                throw new InvalidSimulationValueException("Configured compressor speed must be positive.");
            }
        }
    }

    public record ValveConfiguration(String characteristicReference, String characteristicRevisionId,
            String characteristicEvidenceReference, BigDecimal configuredOpeningFraction) {
        public ValveConfiguration {
            characteristicReference = required(characteristicReference, "Valve characteristic reference");
            characteristicRevisionId = required(characteristicRevisionId, "Valve characteristic revision");
            characteristicEvidenceReference = required(characteristicEvidenceReference, "Valve characteristic evidence");
            if (configuredOpeningFraction == null || configuredOpeningFraction.signum() < 0
                    || configuredOpeningFraction.compareTo(BigDecimal.ONE) > 0) {
                throw new InvalidSimulationValueException("Configured opening must be between zero and one inclusive.");
            }
        }
    }

    private static String required(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new InvalidSimulationValueException(field + " must not be blank.");
        }
        return value.trim();
    }
}
