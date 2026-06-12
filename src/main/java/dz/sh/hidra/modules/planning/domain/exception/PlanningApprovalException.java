/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningApprovalException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.exception
 *
 * @Description : Planning approval exception.
 *
 */
package dz.sh.hidra.modules.planning.domain.exception;

/**
 * Raised when a planning approval rule is violated.
 */
public class PlanningApprovalException extends PlanningDomainException {

    private static final long serialVersionUID = -314291883050773518L;

	public PlanningApprovalException(String message) {
        super(message);
    }
}
