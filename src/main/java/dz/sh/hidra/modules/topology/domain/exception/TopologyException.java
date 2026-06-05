/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.exception
 *
 * @Description : Base topology domain exception.
 *
 */
package dz.sh.hidra.modules.topology.domain.exception;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;

/**
 * Base exception for topology domain failures.
 *
 * <p>Business role:
 * This exception represents violations or failures inside the topology bounded context.
 *
 * <p>Architecture role:
 * This is a pure topology domain exception. It must not depend on Spring, JPA, REST DTOs,
 * application services, persistence adapters, identity implementation, organization implementation,
 * measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Usage:
 * Throw this exception or a subtype when a topology domain rule is violated.
 */
public class TopologyException extends BusinessRuleViolationException {

    private static final long serialVersionUID = 7750382413268893001L;

    /**
     * Creates a topology exception.
     *
     * @param message domain failure message
     */
    public TopologyException(String message) {
        super(message);
    }

    /**
     * Creates a topology exception with a cause.
     *
     * @param message domain failure message
     * @param cause original cause
     */
    public TopologyException(String message, Throwable cause) {
        super(message, cause);
    }
}
