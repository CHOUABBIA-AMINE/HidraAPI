/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateOperationalPlanUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.port.in
 *
 * @Description : Use case for creating operational plans.
 *
 */
package dz.sh.hidra.modules.planning.application.port.in;

import dz.sh.hidra.modules.planning.application.command.CreateOperationalPlanCommand;
import dz.sh.hidra.modules.planning.application.dto.OperationalPlanSummaryDto;

/**
 * Use case for creating operational plans.
 */
public interface CreateOperationalPlanUseCase {

    OperationalPlanSummaryDto createOperationalPlan(CreateOperationalPlanCommand command);
}
