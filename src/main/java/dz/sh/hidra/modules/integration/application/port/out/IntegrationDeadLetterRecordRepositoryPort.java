/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationDeadLetterRecordRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
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

    /** Validate optional evidence and authenticated new resolution; preserve recorded resolver provenance. */
    IntegrationDeadLetterRecord save(IntegrationDeadLetterRecord model);

    Optional<IntegrationDeadLetterRecord> findById(String id);
}
