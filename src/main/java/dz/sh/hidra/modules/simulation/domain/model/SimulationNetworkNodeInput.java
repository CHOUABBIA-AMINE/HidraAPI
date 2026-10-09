/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationNetworkNodeInput
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Captures explicit immutable node identity and elevation in meters.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.math.BigDecimal;

public record SimulationNetworkNodeInput(String id, BigDecimal elevationMeters) {
    public SimulationNetworkNodeInput {
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("Node identity must not be blank.");
        }
        id = id.trim();
        if (elevationMeters == null) {
            throw new InvalidSimulationValueException("Node elevation in meters is required.");
        }
    }
}
