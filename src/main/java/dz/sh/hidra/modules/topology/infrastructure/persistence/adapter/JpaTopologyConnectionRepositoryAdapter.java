/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTopologyConnectionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed TopologyConnection adapter with catalog and segment validation.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.TopologyConnectionRepositoryPort;
import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.TopologyConnection;
import dz.sh.hidra.modules.topology.domain.value.ConnectionTypeReference;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.ConnectionTypeJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.ConnectionTypeJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.TopologyConnectionJpaRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class JpaTopologyConnectionRepositoryAdapter
        implements TopologyConnectionRepositoryPort {

    private final TopologyConnectionJpaRepository repository;
    private final ConnectionTypeJpaRepository typeRepository;

    public JpaTopologyConnectionRepositoryAdapter(
            TopologyConnectionJpaRepository repository,
            ConnectionTypeJpaRepository typeRepository
    ) {
        this.repository = Objects.requireNonNull(
                repository,
                "TopologyConnectionJpaRepository must not be null."
        );
        this.typeRepository = Objects.requireNonNull(
                typeRepository,
                "ConnectionTypeJpaRepository must not be null."
        );
    }

    @Override
    public TopologyConnection save(TopologyConnection model) {
        Objects.requireNonNull(model, "TopologyConnection must not be null.");

        ConnectionTypeJpaEntity typeEntity = typeRepository
                .findById(model.connectionType().id())
                .filter(ConnectionTypeJpaEntity::active)
                .orElseThrow(() -> new InvalidTopologyValueException(
                        "TopologyConnection connection type must resolve to an active catalog entry: "
                                + model.connectionType().id()
                ));

        if (!typeEntity.code().equals(model.connectionType().code())) {
            throw new InvalidTopologyValueException(
                    "TopologyConnection connection type id/code reference is inconsistent."
            );
        }

        if (model.pipelineSegmentId() != null
                && !repository.existsPipelineSegment(model.pipelineSegmentId())) {
            throw new InvalidTopologyValueException(
                    "TopologyConnection pipeline segment does not exist: "
                            + model.pipelineSegmentId()
            );
        }

        return TopologyPersistenceMapper.toDomain(
                repository.save(TopologyPersistenceMapper.toEntity(model, typeEntity))
        );
    }

    @Override
    public Optional<TopologyConnection> findById(String id) {
        return repository.findById(id).map(TopologyPersistenceMapper::toDomain);
    }

    @Override
    public Optional<ConnectionTypeReference> findTypeByCode(String code) {
        if (code == null || code.isBlank()) {
            return Optional.empty();
        }
        return typeRepository.findByCodeAndActiveTrue(code.trim())
                .map(TopologyPersistenceMapper::toDomain);
    }
}
