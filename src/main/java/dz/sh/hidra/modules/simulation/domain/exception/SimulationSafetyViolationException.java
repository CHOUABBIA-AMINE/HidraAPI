/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationSafetyViolationException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.exception
 *
 * @Description : Simulation safety policy violation exception.
 *
 */
package dz.sh.hidra.modules.simulation.domain.exception;

/**
 * Raised when simulation attempts unsafe field actuation or foreign-table ownership.
 */
public class SimulationSafetyViolationException extends SimulationDomainException {

    private static final long serialVersionUID = 329402351312288012L;

	public SimulationSafetyViolationException(String message) {
        super(message);
    }
}
