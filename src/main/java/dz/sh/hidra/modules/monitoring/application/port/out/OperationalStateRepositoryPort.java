/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalStateRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.port.out
 *
 * @Description : Repository port for OperationalState.
 *
 */
package dz.sh.hidra.modules.monitoring.application.port.out;

import dz.sh.hidra.modules.monitoring.domain.model.OperationalState;

import java.util.Optional;

/**
 * Repository port for OperationalState.
 */
public interface OperationalStateRepositoryPort {

    OperationalState save(OperationalState model);

    Optional<OperationalState> findById(String id);
}
