/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationSchemaVersionRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.out
 *
 * @Description : Repository port for IntegrationSchemaVersion.
 *
 */
package dz.sh.hidra.modules.integration.application.port.out;

import dz.sh.hidra.modules.integration.domain.model.IntegrationSchemaVersion;

import java.util.Optional;

/**
 * Repository port for IntegrationSchemaVersion.
 */
public interface IntegrationSchemaVersionRepositoryPort {

    IntegrationSchemaVersion save(IntegrationSchemaVersion model);

    Optional<IntegrationSchemaVersion> findById(String id);
}
