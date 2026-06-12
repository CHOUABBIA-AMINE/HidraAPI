/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceStrategyRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.out
 *
 * @Description : Repository port for MaintenanceStrategy.
 *
 */
package dz.sh.hidra.modules.assets.application.port.out;

import dz.sh.hidra.modules.assets.domain.model.MaintenanceStrategy;

import java.util.Optional;

/**
 * Repository port for MaintenanceStrategy.
 */
public interface MaintenanceStrategyRepositoryPort {

    MaintenanceStrategy save(MaintenanceStrategy model);

    Optional<MaintenanceStrategy> findById(String id);
}
