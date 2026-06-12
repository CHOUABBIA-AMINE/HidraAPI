/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : BusinessRuleViolationException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.exception
 *
 * @Description : Represents a generic violated domain rule.
 *
 */
package dz.sh.hidra.kernel.exception;

/**
 * Generic exception for violated domain rules.
 */
public class BusinessRuleViolationException extends DomainException {

    private static final long serialVersionUID = -5995784243830237905L;

	public BusinessRuleViolationException(String message) {
        super(message);
    }

    public BusinessRuleViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
