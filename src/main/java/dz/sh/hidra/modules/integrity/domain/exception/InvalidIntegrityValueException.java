/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidIntegrityValueException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.exception
 *
 * @Description : Invalid integrity value exception.
 *
 */
package dz.sh.hidra.modules.integrity.domain.exception;

/**
 * Raised when an integrity value is invalid.
 */
public class InvalidIntegrityValueException extends IntegrityDomainException {

    private static final long serialVersionUID = 400433047452992231L;

	public InvalidIntegrityValueException(String message) {
        super(message);
    }
}
