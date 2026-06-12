/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationOutboundRecordRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.out
 *
 * @Description : Repository port for IntegrationOutboundRecord.
 *
 */
package dz.sh.hidra.modules.integration.application.port.out;

import dz.sh.hidra.modules.integration.domain.model.IntegrationOutboundRecord;

import java.util.Optional;

/**
 * Repository port for IntegrationOutboundRecord.
 */
public interface IntegrationOutboundRecordRepositoryPort {

    IntegrationOutboundRecord save(IntegrationOutboundRecord model);

    Optional<IntegrationOutboundRecord> findById(String id);
}
