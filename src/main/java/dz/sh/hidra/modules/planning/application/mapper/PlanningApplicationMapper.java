/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningApplicationMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.mapper
 *
 * @Description : Maps planning domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.planning.application.mapper;

import dz.sh.hidra.modules.planning.application.dto.OperationalPlanSummaryDto;
import dz.sh.hidra.modules.planning.application.dto.PlanningPeriodSummaryDto;
import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import dz.sh.hidra.modules.planning.domain.model.PlanningPeriod;

/**
 * Maps planning domain models to DTOs.
 */
public final class PlanningApplicationMapper {

    private PlanningApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static PlanningPeriodSummaryDto toSummary(PlanningPeriod period) {
        return new PlanningPeriodSummaryDto(period.id(), period.code(), period.nameFr(), period.periodStart(), period.periodEnd(), period.timeZone(), period.status());
    }

    public static OperationalPlanSummaryDto toSummary(OperationalPlan plan) {
        return new OperationalPlanSummaryDto(plan.id(), plan.periodId(), plan.code(), plan.nameFr(), plan.topologyScopeType(), plan.topologyScopeId(), plan.status(), plan.approvedRevisionId());
    }
}
