/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ScopedConfigurationOverrideRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.port.out
 *
 * @Description : Repository port for ScopedConfigurationOverride.
 *
 */
package dz.sh.hidra.modules.configuration.application.port.out;

import dz.sh.hidra.modules.configuration.domain.model.ScopedConfigurationOverride;

import java.util.Optional;

/**
 * Repository port for ScopedConfigurationOverride.
 */
public interface ScopedConfigurationOverrideRepositoryPort {

    ScopedConfigurationOverride save(ScopedConfigurationOverride model);

    Optional<ScopedConfigurationOverride> findById(String id);
}
