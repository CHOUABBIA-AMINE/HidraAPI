/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningPeriodCodeConflictException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.exception
 *
 * @Description : Signals a duplicate planning-period code in its company.
 *
 */
package dz.sh.hidra.modules.planning.domain.exception;

public final class PlanningPeriodCodeConflictException extends PlanningDomainException {

    private static final long serialVersionUID = 1L;

    public PlanningPeriodCodeConflictException() {
        super("A planning period with this code already exists in the selected company.");
    }
}
