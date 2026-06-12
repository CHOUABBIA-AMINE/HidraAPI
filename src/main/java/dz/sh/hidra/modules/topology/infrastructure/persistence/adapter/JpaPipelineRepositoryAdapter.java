/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPipelineRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for Pipeline.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Optional;
@Component
public class JpaPipelineRepositoryAdapter implements PipelineRepositoryPort {
    private final PipelineJpaRepository repository;
    public JpaPipelineRepositoryAdapter(PipelineJpaRepository repository) { this.repository = Objects.requireNonNull(repository, "PipelineJpaRepository must not be null."); }
    public Pipeline save(Pipeline model) { return TopologyPersistenceMapper.toDomain(repository.save(TopologyPersistenceMapper.toEntity(model))); }
    public Optional<Pipeline> findById(String id) { return repository.findById(id).map(TopologyPersistenceMapper::toDomain); }
}
