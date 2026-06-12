/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingDomainException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.exception
 *
 * @Description : Base reporting domain exception.
 *
 */
package dz.sh.hidra.modules.reporting.domain.exception;

/**
 * Base exception for reporting domain failures.
 */
public class ReportingDomainException extends RuntimeException {

    private static final long serialVersionUID = -6813484086462934700L;

	public ReportingDomainException(String message) {
        super(requireMessage(message));
    }

    public ReportingDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Reporting exception message must not be null or blank.");
        }
        return message.trim();
    }
}
