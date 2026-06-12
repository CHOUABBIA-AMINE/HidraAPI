/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationDeadLetterRecordRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.out
 *
 * @Description : Repository port for IntegrationDeadLetterRecord.
 *
 */
package dz.sh.hidra.modules.integration.application.port.out;

import dz.sh.hidra.modules.integration.domain.model.IntegrationDeadLetterRecord;

import java.util.Optional;

/**
 * Repository port for IntegrationDeadLetterRecord.
 */
public interface IntegrationDeadLetterRecordRepositoryPort {

    IntegrationDeadLetterRecord save(IntegrationDeadLetterRecord model);

    Optional<IntegrationDeadLetterRecord> findById(String id);
}
