/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyValidationException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.exception
 *
 * @Description : Topology validation exception.
 *
 */
package dz.sh.hidra.modules.topology.domain.exception;

/**
 * Exception raised when topology validation fails.
 *
 * <p>Business role:
 * This exception is used by topology policies and domain services to reject invalid physical
 * topology states, connections, facilities, appurtenances, and lifecycle transitions.
 *
 * <p>Architecture role:
 * This is a pure topology domain exception. It must not depend on Spring, JPA, REST DTOs,
 * application services, persistence adapters, identity implementation, organization implementation,
 * measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Usage:
 * Throw this exception when a topology validation rule is violated.
 */
public final class TopologyValidationException extends TopologyException {

    private static final long serialVersionUID = 2341402766919967219L;

    /**
     * Creates a topology validation exception.
     *
     * @param message validation failure message
     */
    public TopologyValidationException(String message) {
        super(message);
    }

    /**
     * Creates a topology validation exception with a cause.
     *
     * @param message validation failure message
     * @param cause original cause
     */
    public TopologyValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
