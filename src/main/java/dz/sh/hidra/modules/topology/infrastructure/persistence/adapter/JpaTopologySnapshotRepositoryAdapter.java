/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTopologySnapshotRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for TopologySnapshot.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.TopologySnapshotRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.TopologySnapshot;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.TopologySnapshotJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Optional;
@Component
public class JpaTopologySnapshotRepositoryAdapter implements TopologySnapshotRepositoryPort {
    private final TopologySnapshotJpaRepository repository;
    public JpaTopologySnapshotRepositoryAdapter(TopologySnapshotJpaRepository repository) { this.repository = Objects.requireNonNull(repository, "TopologySnapshotJpaRepository must not be null."); }
    public TopologySnapshot save(TopologySnapshot model) { return TopologyPersistenceMapper.toDomain(repository.save(TopologyPersistenceMapper.toEntity(model))); }
    public Optional<TopologySnapshot> findById(String id) { return repository.findById(id).map(TopologyPersistenceMapper::toDomain); }
}
