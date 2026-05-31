/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleAssignmentNotAllowedException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.exception
 *
 * @Description : Exception for invalid identity role assignment operations.
 *
 */
package dz.sh.hidra.modules.identity.domain.exception;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;

/**
 * Exception raised when a role or permission assignment is not allowed.
 *
 * <p>Business role: protects identity role assignment and permission assignment rules,
 * including duplicate assignments and assignment to inactive targets.</p>
 *
 * <p>Architecture role: identity-specific business-rule exception built on the kernel
 * {@link BusinessRuleViolationException} contract without depending on framework,
 * persistence, platform security, or organization code.</p>
 *
 * <p>Validation responsibility: reports failed assignment rules with a clear domain
 * message and optional cause.</p>
 *
 * <p>Usage: throw from role and user aggregate methods when an assignment would violate
 * identity business rules.</p>
 */
public class RoleAssignmentNotAllowedException extends BusinessRuleViolationException {

    private static final long serialVersionUID = -4403427689872677045L;

    public RoleAssignmentNotAllowedException(String message) {
        super(message);
    }

    public RoleAssignmentNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
