/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidAnalyticsValueException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.exception
 *
 * @Description : Invalid analytics value exception.
 *
 */
package dz.sh.hidra.modules.analytics.domain.exception;

/**
 * Raised when an analytics value is invalid.
 */
public class InvalidAnalyticsValueException extends AnalyticsDomainException {

    private static final long serialVersionUID = 4749210170081986236L;

	public InvalidAnalyticsValueException(String message) {
        super(message);
    }
}
