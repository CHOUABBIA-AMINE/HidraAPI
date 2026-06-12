/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceTaskTemplateRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.out
 *
 * @Description : Repository port for MaintenanceTaskTemplate.
 *
 */
package dz.sh.hidra.modules.assets.application.port.out;

import dz.sh.hidra.modules.assets.domain.model.MaintenanceTaskTemplate;

import java.util.Optional;

/**
 * Repository port for MaintenanceTaskTemplate.
 */
public interface MaintenanceTaskTemplateRepositoryPort {

    MaintenanceTaskTemplate save(MaintenanceTaskTemplate model);

    Optional<MaintenanceTaskTemplate> findById(String id);
}
