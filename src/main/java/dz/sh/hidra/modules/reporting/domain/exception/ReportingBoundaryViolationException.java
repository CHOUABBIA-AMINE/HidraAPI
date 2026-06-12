/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingBoundaryViolationException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.exception
 *
 * @Description : Reporting boundary violation exception.
 *
 */
package dz.sh.hidra.modules.reporting.domain.exception;

/**
 * Raised when reporting attempts to mutate source truth, bypass authorization, or embed secrets.
 */
public class ReportingBoundaryViolationException extends ReportingDomainException {

    private static final long serialVersionUID = -8003730205218054047L;

	public ReportingBoundaryViolationException(String message) {
        super(message);
    }
}
