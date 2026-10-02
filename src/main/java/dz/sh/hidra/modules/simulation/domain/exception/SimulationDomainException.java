/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationDomainException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.exception
 *
 * @Description : Base simulation domain exception.
 *
 */
package dz.sh.hidra.modules.simulation.domain.exception;

/**
 * Base exception for simulation domain failures.
 */
public class SimulationDomainException extends RuntimeException {

    private static final long serialVersionUID = -6212548676607287728L;

	public SimulationDomainException(String message) {
        super(requireMessage(message));
    }

    public SimulationDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Simulation exception message must not be null or blank.");
        }
        return message.trim();
    }
}
