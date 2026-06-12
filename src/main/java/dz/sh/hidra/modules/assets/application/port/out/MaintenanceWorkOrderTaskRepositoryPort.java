/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceWorkOrderTaskRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.out
 *
 * @Description : Repository port for MaintenanceWorkOrderTask.
 *
 */
package dz.sh.hidra.modules.assets.application.port.out;

import dz.sh.hidra.modules.assets.domain.model.MaintenanceWorkOrderTask;

import java.util.Optional;

/**
 * Repository port for MaintenanceWorkOrderTask.
 */
public interface MaintenanceWorkOrderTaskRepositoryPort {

    MaintenanceWorkOrderTask save(MaintenanceWorkOrderTask model);

    Optional<MaintenanceWorkOrderTask> findById(String id);
}
