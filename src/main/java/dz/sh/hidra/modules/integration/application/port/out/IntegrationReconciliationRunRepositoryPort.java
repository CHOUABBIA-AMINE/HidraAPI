/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationReconciliationRunRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.out
 *
 * @Description : Repository port for IntegrationReconciliationRun.
 *
 */
package dz.sh.hidra.modules.integration.application.port.out;

import dz.sh.hidra.modules.integration.domain.model.IntegrationReconciliationRun;

import java.util.Optional;

/**
 * Repository port for IntegrationReconciliationRun.
 */
public interface IntegrationReconciliationRunRepositoryPort {

    IntegrationReconciliationRun save(IntegrationReconciliationRun model);

    Optional<IntegrationReconciliationRun> findById(String id);
}
