/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationJobRunRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.out
 *
 * @Description : Repository port for IntegrationJobRun.
 *
 */
package dz.sh.hidra.modules.integration.application.port.out;

import dz.sh.hidra.modules.integration.domain.model.IntegrationJobRun;

import java.util.Optional;

/**
 * Repository port for IntegrationJobRun.
 */
public interface IntegrationJobRunRepositoryPort {

    /**
     * Persists a run. New runs receive their authoritative per-job run number at
     * the persistence/database boundary; updates are transition-validated.
     */
    IntegrationJobRun save(IntegrationJobRun model);

    Optional<IntegrationJobRun> findById(String id);
}
