/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationBoundaryViolationException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.exception
 *
 * @Description : Integration boundary violation exception.
 *
 */
package dz.sh.hidra.modules.integration.domain.exception;

/**
 * Raised when integration attempts to bypass target modules or industrial safety boundaries.
 */
public class IntegrationBoundaryViolationException extends IntegrationDomainException {

    private static final long serialVersionUID = -3630824879167535056L;

	public IntegrationBoundaryViolationException(String message) {
        super(message);
    }
}
