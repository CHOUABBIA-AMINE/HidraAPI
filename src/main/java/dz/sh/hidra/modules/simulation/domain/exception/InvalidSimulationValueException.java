/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidSimulationValueException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.exception
 *
 * @Description : Invalid simulation value exception.
 *
 */
package dz.sh.hidra.modules.simulation.domain.exception;

/**
 * Raised when a simulation value is invalid.
 */
public class InvalidSimulationValueException extends SimulationDomainException {

    private static final long serialVersionUID = 6815918002352828717L;

	public InvalidSimulationValueException(String message) {
        super(message);
    }
}
