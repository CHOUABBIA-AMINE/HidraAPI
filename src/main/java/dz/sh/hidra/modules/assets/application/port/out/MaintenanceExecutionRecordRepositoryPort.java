/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceExecutionRecordRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.out
 *
 * @Description : Repository port for MaintenanceExecutionRecord.
 *
 */
package dz.sh.hidra.modules.assets.application.port.out;

import dz.sh.hidra.modules.assets.domain.model.MaintenanceExecutionRecord;

import java.util.Optional;

/**
 * Repository port for MaintenanceExecutionRecord.
 */
public interface MaintenanceExecutionRecordRepositoryPort {

    MaintenanceExecutionRecord save(MaintenanceExecutionRecord model);

    Optional<MaintenanceExecutionRecord> findById(String id);
}
