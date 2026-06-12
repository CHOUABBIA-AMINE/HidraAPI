/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidIncidentValueException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.exception
 *
 * @Description : Invalid incident value exception.
 *
 */
package dz.sh.hidra.modules.incident.domain.exception;

/**
 * Raised when an incident value is invalid.
 */
public class InvalidIncidentValueException extends IncidentDomainException {

    private static final long serialVersionUID = -2874916873779166633L;

	public InvalidIncidentValueException(String message) {
        super(message);
    }
}
