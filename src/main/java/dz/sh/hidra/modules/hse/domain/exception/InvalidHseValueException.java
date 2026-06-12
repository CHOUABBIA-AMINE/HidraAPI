/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidHseValueException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.exception
 *
 * @Description : Invalid HSE value exception.
 *
 */
package dz.sh.hidra.modules.hse.domain.exception;

/**
 * Raised when an HSE value is invalid.
 */
public class InvalidHseValueException extends HseDomainException {

    private static final long serialVersionUID = 7845475551589958922L;

	public InvalidHseValueException(String message) {
        super(message);
    }
}
