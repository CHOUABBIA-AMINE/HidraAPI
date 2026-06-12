/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EnvironmentalEventRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.out
 *
 * @Description : Repository port for EnvironmentalEvent.
 *
 */
package dz.sh.hidra.modules.hse.application.port.out;

import dz.sh.hidra.modules.hse.domain.model.EnvironmentalEvent;

import java.util.Optional;

/**
 * Repository port for EnvironmentalEvent.
 */
public interface EnvironmentalEventRepositoryPort {

    EnvironmentalEvent save(EnvironmentalEvent model);

    Optional<EnvironmentalEvent> findById(String id);
}
