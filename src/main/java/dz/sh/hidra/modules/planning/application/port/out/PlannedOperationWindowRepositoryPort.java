/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlannedOperationWindowRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.port.out
 *
 * @Description : Repository port for PlannedOperationWindow.
 *
 */
package dz.sh.hidra.modules.planning.application.port.out;

import dz.sh.hidra.modules.planning.domain.model.PlannedOperationWindow;

import java.util.Optional;

/**
 * Repository port for PlannedOperationWindow.
 */
public interface PlannedOperationWindowRepositoryPort {

    PlannedOperationWindow save(PlannedOperationWindow model);

    Optional<PlannedOperationWindow> findById(String id);
}
