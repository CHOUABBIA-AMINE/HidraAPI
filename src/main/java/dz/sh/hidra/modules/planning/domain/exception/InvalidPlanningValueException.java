/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidPlanningValueException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.exception
 *
 * @Description : Invalid planning value exception.
 *
 */
package dz.sh.hidra.modules.planning.domain.exception;

/**
 * Raised when a planning value is invalid.
 */
public class InvalidPlanningValueException extends PlanningDomainException {

    private static final long serialVersionUID = 3013247255013011425L;

	public InvalidPlanningValueException(String message) {
        super(message);
    }
}
