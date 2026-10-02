/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidIntegrationValueException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.exception
 *
 * @Description : Invalid integration value exception.
 *
 */
package dz.sh.hidra.modules.integration.domain.exception;

/**
 * Raised when an integration value is invalid.
 */
public class InvalidIntegrationValueException extends IntegrationDomainException {

    private static final long serialVersionUID = -7643333903985775802L;

	public InvalidIntegrationValueException(String message) {
        super(message);
    }
}
