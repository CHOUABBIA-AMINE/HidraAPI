/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationCatalogTranslationRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.out
 *
 * @Description : Repository port for SimulationCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.out;

import dz.sh.hidra.modules.simulation.domain.model.SimulationCatalogTranslation;

import java.util.Optional;

/**
 * Repository port for SimulationCatalogTranslation.
 */
public interface SimulationCatalogTranslationRepositoryPort {

    SimulationCatalogTranslation save(SimulationCatalogTranslation model);

    Optional<SimulationCatalogTranslation> findById(String id);
}
