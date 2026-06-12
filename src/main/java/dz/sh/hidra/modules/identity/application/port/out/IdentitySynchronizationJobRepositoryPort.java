/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentitySynchronizationJobRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Repository port for IdentitySynchronizationJob.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.model.IdentitySynchronizationJob;

import java.util.Optional;

/**
 * Repository port for IdentitySynchronizationJob.
 */
public interface IdentitySynchronizationJobRepositoryPort {

    IdentitySynchronizationJob save(IdentitySynchronizationJob model);

    Optional<IdentitySynchronizationJob> findById(String id);
}
