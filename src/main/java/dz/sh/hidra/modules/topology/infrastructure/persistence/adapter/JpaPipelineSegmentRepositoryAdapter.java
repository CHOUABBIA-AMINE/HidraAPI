/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPipelineSegmentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PipelineSegment.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.PipelineSegmentRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.PipelineSegment;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineSegmentJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Optional;
@Component
public class JpaPipelineSegmentRepositoryAdapter implements PipelineSegmentRepositoryPort {
    private final PipelineSegmentJpaRepository repository;
    public JpaPipelineSegmentRepositoryAdapter(PipelineSegmentJpaRepository repository) { this.repository = Objects.requireNonNull(repository, "PipelineSegmentJpaRepository must not be null."); }
    public PipelineSegment save(PipelineSegment model) { return TopologyPersistenceMapper.toDomain(repository.save(TopologyPersistenceMapper.toEntity(model))); }
    public Optional<PipelineSegment> findById(String id) { return repository.findById(id).map(TopologyPersistenceMapper::toDomain); }
}
