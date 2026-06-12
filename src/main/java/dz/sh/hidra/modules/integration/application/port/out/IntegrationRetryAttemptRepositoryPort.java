/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationRetryAttemptRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.out
 *
 * @Description : Repository port for IntegrationRetryAttempt.
 *
 */
package dz.sh.hidra.modules.integration.application.port.out;

import dz.sh.hidra.modules.integration.domain.model.IntegrationRetryAttempt;

import java.util.Optional;

/**
 * Repository port for IntegrationRetryAttempt.
 */
public interface IntegrationRetryAttemptRepositoryPort {

    IntegrationRetryAttempt save(IntegrationRetryAttempt model);

    Optional<IntegrationRetryAttempt> findById(String id);
}
