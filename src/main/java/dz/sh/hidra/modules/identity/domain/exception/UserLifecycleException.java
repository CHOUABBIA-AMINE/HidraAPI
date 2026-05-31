/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserLifecycleException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.exception
 *
 * @Description : Exception for invalid identity user lifecycle transitions.
 *
 */
package dz.sh.hidra.modules.identity.domain.exception;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;

/**
 * Exception raised when an identity user lifecycle transition is invalid.
 *
 * <p>Business role: protects valid user lifecycle movement such as activation,
 * suspension, disabling, and changes blocked by disabled status.</p>
 *
 * <p>Architecture role: identity-specific business-rule exception built on the kernel
 * {@link BusinessRuleViolationException} contract without depending on HTTP, Spring,
 * persistence, platform security, or organization code.</p>
 *
 * <p>Validation responsibility: reports invalid lifecycle operations with a clear domain
 * message and optional cause.</p>
 *
 * <p>Usage: throw from the user aggregate when a requested lifecycle transition is not
 * allowed by identity business rules.</p>
 */
public class UserLifecycleException extends BusinessRuleViolationException {

    private static final long serialVersionUID = 9045588178761706530L;

    public UserLifecycleException(String message) {
        super(message);
    }

    public UserLifecycleException(String message, Throwable cause) {
        super(message, cause);
    }
}
