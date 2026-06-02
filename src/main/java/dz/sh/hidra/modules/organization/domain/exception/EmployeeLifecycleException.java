/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeLifecycleException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.exception
 *
 * @Description : Exception for invalid employee lifecycle transitions.
 *
 */
package dz.sh.hidra.modules.organization.domain.exception;

/**
 * Exception for invalid employee lifecycle transitions.
 *
 * <p>Business role:
 * This exception represents an invalid employee lifecycle transition, such as activating a disabled employee or suspending an employee that is not active.
 *
 * <p>Architecture role:
 * This exception belongs to the organization domain layer. It carries organization business
 * failure semantics without depending on REST, HTTP status codes, Spring, JPA, platform,
 * identity, topology, or infrastructure code.
 *
 * <p>Validation:
 * Use this exception when employee status transitions violate the allowed organization lifecycle rules.
 *
 * <p>Usage:
 * Throw this exception from employee aggregate behavior, employee lifecycle policy, or application orchestration that enforces employee lifecycle invariants.
 */
public class EmployeeLifecycleException extends OrganizationDomainException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception with a domain failure message.
     *
     * @param message failure message
     */
    public EmployeeLifecycleException(String message) {
        super(message);
    }

    /**
     * Creates an exception with a domain failure message and root cause.
     *
     * @param message failure message
     * @param cause root cause
     */
    public EmployeeLifecycleException(String message, Throwable cause) {
        super(message, cause);
    }
}
