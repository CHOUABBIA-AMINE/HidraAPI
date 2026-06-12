/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationResultSeriesReferenceRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.out
 *
 * @Description : Repository port for SimulationResultSeriesReference.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.out;

import dz.sh.hidra.modules.simulation.domain.model.SimulationResultSeriesReference;

import java.util.Optional;

/**
 * Repository port for SimulationResultSeriesReference.
 */
public interface SimulationResultSeriesReferenceRepositoryPort {

    SimulationResultSeriesReference save(SimulationResultSeriesReference model);

    Optional<SimulationResultSeriesReference> findById(String id);
}
