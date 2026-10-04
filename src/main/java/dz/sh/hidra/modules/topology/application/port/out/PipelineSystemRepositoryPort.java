/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Repository port for PipelineSystem and its Topology-owned classification catalog.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemType;
import java.util.Optional;

public interface PipelineSystemRepositoryPort {
    PipelineSystem save(PipelineSystem model);

    Optional<PipelineSystem> findById(String id);

    default Optional<PipelineSystemType> findTypeByCode(String code) {
        return Optional.empty();
    }
}
