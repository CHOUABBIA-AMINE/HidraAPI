/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationPipeSegmentInput
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Captures explicit immutable pipe geometry and reference orientation.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.math.BigDecimal;

/** Endpoint orientation is a reference direction; reverse flow remains possible. */
public record SimulationPipeSegmentInput(
        String id,
        String fromNodeId,
        String toNodeId,
        BigDecimal lengthMeters,
        BigDecimal internalDiameterMeters,
        BigDecimal absoluteRoughnessMeters
) {
    public SimulationPipeSegmentInput {
        id = required(id, "Pipe identity");
        fromNodeId = required(fromNodeId, "From-node identity");
        toNodeId = required(toNodeId, "To-node identity");
        if (fromNodeId.equals(toNodeId)) {
            throw new InvalidSimulationValueException("Pipe endpoints must be distinct.");
        }
        if (lengthMeters == null || lengthMeters.signum() <= 0) {
            throw new InvalidSimulationValueException("Pipe length in meters must be positive.");
        }
        if (internalDiameterMeters == null || internalDiameterMeters.signum() <= 0) {
            throw new InvalidSimulationValueException("Pipe internal diameter in meters must be positive.");
        }
        if (absoluteRoughnessMeters == null || absoluteRoughnessMeters.signum() < 0) {
            throw new InvalidSimulationValueException("Pipe absolute roughness in meters must be nonnegative.");
        }
    }

    private static String required(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new InvalidSimulationValueException(field + " must not be blank.");
        }
        return value.trim();
    }
}
