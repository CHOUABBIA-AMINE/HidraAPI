/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEvidenceLinkRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.out
 *
 * @Description : Repository port for SimulationEvidenceLink.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.out;

import dz.sh.hidra.modules.simulation.domain.model.SimulationEvidenceLink;

import java.util.Optional;

/**
 * Repository port for SimulationEvidenceLink.
 */
public interface SimulationEvidenceLinkRepositoryPort {

    SimulationEvidenceLink save(SimulationEvidenceLink model);

    Optional<SimulationEvidenceLink> findById(String id);
}
