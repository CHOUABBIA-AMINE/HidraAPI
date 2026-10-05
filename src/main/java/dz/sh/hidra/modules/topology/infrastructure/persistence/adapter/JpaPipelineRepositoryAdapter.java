/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPipelineRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for Pipeline and its classification catalog.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.value.PipelineType;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineTypeJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineTypeJpaRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class JpaPipelineRepositoryAdapter implements PipelineRepositoryPort {

    private final PipelineJpaRepository repository;
    private final PipelineTypeJpaRepository typeRepository;

    public JpaPipelineRepositoryAdapter(
            PipelineJpaRepository repository,
            PipelineTypeJpaRepository typeRepository
    ) {
        this.repository = Objects.requireNonNull(
                repository,
                "PipelineJpaRepository must not be null."
        );
        this.typeRepository = Objects.requireNonNull(
                typeRepository,
                "PipelineTypeJpaRepository must not be null."
        );
    }

    @Override
    public Pipeline save(Pipeline model) {
        PipelineTypeJpaEntity typeEntity = typeRepository.findById(model.pipelineType().id())
                .orElseThrow(() -> new InvalidTopologyValueException(
                        "Unknown Pipeline type id: " + model.pipelineType().id()
                ));

        if (!typeEntity.code().equals(model.pipelineType().code())) {
            throw new InvalidTopologyValueException(
                    "Pipeline type id/code reference is inconsistent."
            );
        }

        return TopologyPersistenceMapper.toDomain(
                repository.save(TopologyPersistenceMapper.toEntity(model, typeEntity))
        );
    }

    @Override
    public Optional<Pipeline> findById(String id) {
        return repository.findById(id).map(TopologyPersistenceMapper::toDomain);
    }

    @Override
    public Optional<PipelineType> findTypeByCode(String code) {
        if (code == null || code.isBlank()) {
            return Optional.empty();
        }
        return typeRepository.findByCode(code.trim()).map(TopologyPersistenceMapper::toDomain);
    }
}
