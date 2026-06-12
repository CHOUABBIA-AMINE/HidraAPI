/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidCustodyValueException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.exception
 *
 * @Description : Invalid custody value exception.
 *
 */
package dz.sh.hidra.modules.custody.domain.exception;

/**
 * Raised when a custody value is invalid.
 */
public class InvalidCustodyValueException extends CustodyDomainException {

    private static final long serialVersionUID = 3755723705568589033L;

	public InvalidCustodyValueException(String message) {
        super(message);
    }
}
