/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.controller
 *
 * @Description : Framework-neutral planning controller contract.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.controller;

import dz.sh.hidra.modules.planning.api.rest.request.CreateOperationalPlanRequest;
import dz.sh.hidra.modules.planning.api.rest.request.CreatePlanningPeriodRequest;
import dz.sh.hidra.modules.planning.api.rest.request.UpdateOperationalPlanRequest;
import dz.sh.hidra.modules.planning.api.rest.response.OperationalPlanResponse;
import dz.sh.hidra.modules.planning.api.rest.response.OperationalPlanUpdateResponse;
import dz.sh.hidra.modules.planning.api.rest.response.PlanningPeriodResponse;

/**
 * Framework-neutral planning controller contract.
 */
public interface PlanningController {

    OperationalPlanResponse createOperationalPlan(CreateOperationalPlanRequest request);

    PlanningPeriodResponse createPlanningPeriod(CreatePlanningPeriodRequest request);

    OperationalPlanUpdateResponse updateOperationalPlan(String id, UpdateOperationalPlanRequest request);
}
