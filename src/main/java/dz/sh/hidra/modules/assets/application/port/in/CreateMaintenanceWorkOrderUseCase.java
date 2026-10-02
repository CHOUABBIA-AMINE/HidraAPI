/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateMaintenanceWorkOrderUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.in
 *
 * @Description : Use case for creating maintenance work orders.
 *
 */
package dz.sh.hidra.modules.assets.application.port.in;

import dz.sh.hidra.modules.assets.application.command.CreateMaintenanceWorkOrderCommand;
import dz.sh.hidra.modules.assets.application.dto.MaintenanceWorkOrderSummaryDto;

/**
 * Use case for creating maintenance work orders.
 */
public interface CreateMaintenanceWorkOrderUseCase {

    MaintenanceWorkOrderSummaryDto createMaintenanceWorkOrder(CreateMaintenanceWorkOrderCommand command);
}
