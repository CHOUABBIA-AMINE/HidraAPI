/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DomainException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.exception
 *
 * @Description : Defines the base exception for domain-level failures.
 *
 */
package dz.sh.hidra.kernel.exception;

/**
 * Base exception for domain-level failures.
 */
public abstract class DomainException extends RuntimeException {

    private static final long serialVersionUID = 7440984253008933840L;

	protected DomainException(String message) {
        super(requireMessage(message));
    }

    protected DomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Domain exception message must not be null or blank.");
        }
        return message.trim();
    }
}
