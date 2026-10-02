/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePlanningPeriodUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.port.in
 *
 * @Description : Use case for creating planning periods.
 *
 */
package dz.sh.hidra.modules.planning.application.port.in;

import dz.sh.hidra.modules.planning.application.command.CreatePlanningPeriodCommand;
import dz.sh.hidra.modules.planning.application.dto.PlanningPeriodSummaryDto;

/**
 * Use case for creating planning periods.
 */
public interface CreatePlanningPeriodUseCase {

    PlanningPeriodSummaryDto createPlanningPeriod(CreatePlanningPeriodCommand command);
}
