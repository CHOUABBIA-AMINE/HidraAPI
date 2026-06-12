/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionDomainException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.exception
 *
 * @Description : Base leak detection domain exception.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.exception;

/**
 * Base exception for leak detection domain failures.
 */
public class LeakDetectionDomainException extends RuntimeException {

    private static final long serialVersionUID = -6284840867819134401L;

	public LeakDetectionDomainException(String message) {
        super(requireMessage(message));
    }

    public LeakDetectionDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Leak detection exception message must not be null or blank.");
        }
        return message.trim();
    }
}
