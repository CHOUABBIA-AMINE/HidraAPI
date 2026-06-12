/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidPartyValueException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.exception
 *
 * @Description : Invalid party value exception.
 *
 */
package dz.sh.hidra.modules.party.domain.exception;

/**
 * Raised when a party value is invalid.
 */
public class InvalidPartyValueException extends PartyDomainException {

    private static final long serialVersionUID = 8980823574743682751L;

	public InvalidPartyValueException(String message) {
        super(message);
    }
}
