/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationExchangeMessageRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.out
 *
 * @Description : Repository port for IntegrationExchangeMessage.
 *
 */
package dz.sh.hidra.modules.integration.application.port.out;

import dz.sh.hidra.modules.integration.domain.model.IntegrationExchangeMessage;

import java.util.Optional;

/**
 * Repository port for IntegrationExchangeMessage.
 */
public interface IntegrationExchangeMessageRepositoryPort {

    IntegrationExchangeMessage save(IntegrationExchangeMessage model);

    Optional<IntegrationExchangeMessage> findById(String id);
}
