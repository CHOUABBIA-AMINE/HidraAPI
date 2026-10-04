/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPipelineSystemRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PipelineSystem and its classification catalog.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemRepositoryPort;
import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemType;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineSystemTypeJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineSystemJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineSystemTypeJpaRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class JpaPipelineSystemRepositoryAdapter implements PipelineSystemRepositoryPort {
    private final PipelineSystemJpaRepository repository;
    private final PipelineSystemTypeJpaRepository typeRepository;

    public JpaPipelineSystemRepositoryAdapter(
            PipelineSystemJpaRepository repository,
            PipelineSystemTypeJpaRepository typeRepository
    ) {
        this.repository = Objects.requireNonNull(
                repository,
                "PipelineSystemJpaRepository must not be null."
        );
        this.typeRepository = Objects.requireNonNull(
                typeRepository,
                "PipelineSystemTypeJpaRepository must not be null."
        );
    }

    public PipelineSystem save(PipelineSystem model) {
        PipelineSystemTypeJpaEntity typeEntity = typeRepository.findById(model.systemType().id())
                .orElseThrow(() -> new InvalidTopologyValueException(
                        "Unknown PipelineSystem system type id: " + model.systemType().id()
                ));

        if (!typeEntity.code().equals(model.systemType().code())) {
            throw new InvalidTopologyValueException(
                    "PipelineSystem system type id/code reference is inconsistent."
            );
        }

        return TopologyPersistenceMapper.toDomain(
                repository.save(TopologyPersistenceMapper.toEntity(model, typeEntity))
        );
    }

    public Optional<PipelineSystem> findById(String id) {
        return repository.findById(id).map(TopologyPersistenceMapper::toDomain);
    }

    @Override
    public Optional<PipelineSystemType> findTypeByCode(String code) {
        return typeRepository.findByCode(code).map(TopologyPersistenceMapper::toDomain);
    }
}
