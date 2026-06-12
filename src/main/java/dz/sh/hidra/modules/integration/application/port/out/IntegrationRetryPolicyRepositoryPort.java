/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationRetryPolicyRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.out
 *
 * @Description : Repository port for IntegrationRetryPolicy.
 *
 */
package dz.sh.hidra.modules.integration.application.port.out;

import dz.sh.hidra.modules.integration.domain.model.IntegrationRetryPolicy;

import java.util.Optional;

/**
 * Repository port for IntegrationRetryPolicy.
 */
public interface IntegrationRetryPolicyRepositoryPort {

    IntegrationRetryPolicy save(IntegrationRetryPolicy model);

    Optional<IntegrationRetryPolicy> findById(String id);
}
