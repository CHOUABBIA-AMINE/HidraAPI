/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationId
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.value
 *
 * @Description : Stable simulation identifier.
 *
 */
package dz.sh.hidra.modules.simulation.domain.value;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;

import java.util.UUID;

/**
 * Stable simulation identifier.
 *
 * @param value identifier value
 */
public record SimulationId(String value) {

    public SimulationId {
        value = requireText(value, "Simulation ID must not be null or blank.");
    }

    public static SimulationId of(String value) {
        return new SimulationId(value);
    }

    public static SimulationId newId() {
        return new SimulationId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidSimulationValueException(message);
        }
        return value.trim();
    }
}
