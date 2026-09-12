/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UpdateOperationalPlanUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.port.in
 *
 * @Description : Use case for concurrency-protected operational-plan metadata updates.
 *
 */
package dz.sh.hidra.modules.planning.application.port.in;

import dz.sh.hidra.modules.planning.application.command.UpdateOperationalPlanCommand;
import java.time.Instant;

/**
 * Updates one operational plan when the supplied backend concurrency token is current.
 */
public interface UpdateOperationalPlanUseCase {

    OperationalPlanUpdateResult updateOperationalPlan(UpdateOperationalPlanCommand command);

    record OperationalPlanUpdateResult(
            String id,
            String nameAr,
            String nameFr,
            String nameEn,
            String responsibleOrganizationUnitId,
            Instant updatedAt
    ) {
    }
}
