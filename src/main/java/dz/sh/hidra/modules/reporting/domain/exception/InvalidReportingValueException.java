/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidReportingValueException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.exception
 *
 * @Description : Invalid reporting value exception.
 *
 */
package dz.sh.hidra.modules.reporting.domain.exception;

/**
 * Raised when a reporting value is invalid.
 */
public class InvalidReportingValueException extends ReportingDomainException {

    private static final long serialVersionUID = 8209194858472253033L;

	public InvalidReportingValueException(String message) {
        super(message);
    }
}
