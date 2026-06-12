/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : BlockedPartySelectionException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.exception
 *
 * @Description : Blocked party selection exception.
 *
 */
package dz.sh.hidra.modules.party.domain.exception;

/**
 * Raised when a blocked party is selected without explicit override.
 */
public class BlockedPartySelectionException extends PartyDomainException {

    private static final long serialVersionUID = -4913611167964671226L;

	public BlockedPartySelectionException(String message) {
        super(message);
    }
}
