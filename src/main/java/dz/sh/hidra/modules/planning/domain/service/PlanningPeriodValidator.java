/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningPeriodValidator
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.service
 *
 * @Description : Validates planning period intervals.
 *
 */
package dz.sh.hidra.modules.planning.domain.service;

import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.planning.domain.model.PlanningPeriod;

/**
 * Validates planning period intervals.
 */
public class PlanningPeriodValidator {

    public void validate(PlanningPeriod period) {
        if (period == null) {
            throw new InvalidPlanningValueException("Planning period must not be null.");
        }
        if (period.periodStart() == null || period.periodEnd() == null || !period.periodStart().isBefore(period.periodEnd())) {
            throw new InvalidPlanningValueException("Planning period start must be before period end.");
        }
    }
}
