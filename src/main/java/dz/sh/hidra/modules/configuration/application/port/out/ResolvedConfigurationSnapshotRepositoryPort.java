/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResolvedConfigurationSnapshotRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.port.out
 *
 * @Description : Repository port for ResolvedConfigurationSnapshot.
 *
 */
package dz.sh.hidra.modules.configuration.application.port.out;

import dz.sh.hidra.modules.configuration.domain.model.ResolvedConfigurationSnapshot;

import java.util.Optional;

/**
 * Repository port for ResolvedConfigurationSnapshot.
 */
public interface ResolvedConfigurationSnapshotRepositoryPort {

    ResolvedConfigurationSnapshot save(ResolvedConfigurationSnapshot model);

    Optional<ResolvedConfigurationSnapshot> findById(String id);
}
