/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyDomainException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.exception
 *
 * @Description : Base party domain exception.
 *
 */
package dz.sh.hidra.modules.party.domain.exception;

/**
 * Base exception for party domain failures.
 */
public class PartyDomainException extends RuntimeException {

    private static final long serialVersionUID = 1006542602733611537L;

	public PartyDomainException(String message) {
        super(requireMessage(message));
    }

    public PartyDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Party exception message must not be null or blank.");
        }
        return message.trim();
    }
}
