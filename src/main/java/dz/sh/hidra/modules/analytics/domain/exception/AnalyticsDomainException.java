/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDomainException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.exception
 *
 * @Description : Base analytics domain exception.
 *
 */
package dz.sh.hidra.modules.analytics.domain.exception;

/**
 * Base exception for analytics domain failures.
 */
public class AnalyticsDomainException extends RuntimeException {

    private static final long serialVersionUID = 1093938262865473087L;

	public AnalyticsDomainException(String message) {
        super(requireMessage(message));
    }

    public AnalyticsDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Analytics exception message must not be null or blank.");
        }
        return message.trim();
    }
}
