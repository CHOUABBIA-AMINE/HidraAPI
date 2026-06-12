/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPipelineSystemFacilityRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PipelineSystemFacility.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemFacilityRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystemFacility;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineSystemFacilityJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Optional;
@Component
public class JpaPipelineSystemFacilityRepositoryAdapter implements PipelineSystemFacilityRepositoryPort {
    private final PipelineSystemFacilityJpaRepository repository;
    public JpaPipelineSystemFacilityRepositoryAdapter(PipelineSystemFacilityJpaRepository repository) { this.repository = Objects.requireNonNull(repository, "PipelineSystemFacilityJpaRepository must not be null."); }
    public PipelineSystemFacility save(PipelineSystemFacility model) { return TopologyPersistenceMapper.toDomain(repository.save(TopologyPersistenceMapper.toEntity(model))); }
    public Optional<PipelineSystemFacility> findById(String id) { return repository.findById(id).map(TopologyPersistenceMapper::toDomain); }
}
