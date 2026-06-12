/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationSafetyGuard
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.service
 *
 * @Description : Guards simulation against unsafe field actuation.
 *
 */
package dz.sh.hidra.modules.simulation.domain.service;

import dz.sh.hidra.modules.simulation.domain.exception.SimulationSafetyViolationException;
import dz.sh.hidra.modules.simulation.domain.policy.SimulationSafetyPolicy;

/**
 * Guards simulation against unsafe field actuation.
 */
public class SimulationSafetyGuard {

    public void ensureNoFieldActuation(String operationName) {
        if (SimulationSafetyPolicy.isForbiddenActuation(operationName)) {
            throw new SimulationSafetyViolationException("Simulation must not actuate field equipment or send SCADA/PLC/RTU/SIS/ESD commands.");
        }
    }

    public void ensureNoForeignTableWrite(String tableName) {
        if (SimulationSafetyPolicy.isForeignTableWrite(tableName)) {
            throw new SimulationSafetyViolationException("Simulation must not write directly to another module table.");
        }
    }
}
