/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningRevisionConflictException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.exception
 *
 * @Description : Signals a stale or non-current planning revision mutation.
 *
 */
package dz.sh.hidra.modules.planning.domain.exception;

public final class PlanningRevisionConflictException extends PlanningDomainException {

    private static final long serialVersionUID = 1L;

    public PlanningRevisionConflictException(String message) {
        super(message);
    }
}
