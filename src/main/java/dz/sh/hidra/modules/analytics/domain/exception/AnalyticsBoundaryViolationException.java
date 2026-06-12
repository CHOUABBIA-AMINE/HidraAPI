/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsBoundaryViolationException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.exception
 *
 * @Description : Analytics boundary violation exception.
 *
 */
package dz.sh.hidra.modules.analytics.domain.exception;

/**
 * Raised when analytics attempts to own operational source-of-truth behavior.
 */
public class AnalyticsBoundaryViolationException extends AnalyticsDomainException {

    private static final long serialVersionUID = 2965228675739775530L;

	public AnalyticsBoundaryViolationException(String message) {
        super(message);
    }
}
