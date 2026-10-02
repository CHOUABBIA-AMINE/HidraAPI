/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentLifecycleViolationException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.exception
 *
 * @Description : Incident lifecycle violation exception.
 *
 */
package dz.sh.hidra.modules.incident.domain.exception;

/**
 * Raised when an incident lifecycle transition is invalid.
 */
public class IncidentLifecycleViolationException extends IncidentDomainException {

    private static final long serialVersionUID = 7156614934770999974L;

	public IncidentLifecycleViolationException(String message) {
        super(message);
    }
}
