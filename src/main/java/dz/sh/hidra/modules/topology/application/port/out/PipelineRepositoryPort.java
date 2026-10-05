/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Repository port for Pipeline and its Topology-owned classification catalog.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.value.PipelineType;
import java.util.Optional;
public interface PipelineRepositoryPort {
    Pipeline save(Pipeline model);
    Optional<Pipeline> findById(String id);

    default Optional<PipelineType> findTypeByCode(String code) {
        return Optional.empty();
    }
}
