/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignmentNotAllowedException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.exception
 *
 * @Description : Exception for employee assignment violations.
 *
 */
package dz.sh.hidra.modules.organization.domain.exception;

/**
 * Exception for employee assignment violations.
 *
 * <p>Business role:
 * This exception represents invalid employee assignment behavior, such as assigning a disabled employee, assigning to a disabled organization unit, or creating duplicate active assignments to the same unit and position.
 *
 * <p>Architecture role:
 * This exception belongs to the organization domain layer. It carries organization business
 * failure semantics without depending on REST, HTTP status codes, Spring, JPA, platform,
 * identity, topology, or infrastructure code.
 *
 * <p>Validation:
 * Use this exception when employee assignment rules are violated by an aggregate, policy, or domain service.
 *
 * <p>Usage:
 * Throw this exception from employee assignment behavior or policy code instead of leaking generic infrastructure or validation exceptions.
 */
public class EmployeeAssignmentNotAllowedException extends OrganizationDomainException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception with a domain failure message.
     *
     * @param message failure message
     */
    public EmployeeAssignmentNotAllowedException(String message) {
        super(message);
    }

    /**
     * Creates an exception with a domain failure message and root cause.
     *
     * @param message failure message
     * @param cause root cause
     */
    public EmployeeAssignmentNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
