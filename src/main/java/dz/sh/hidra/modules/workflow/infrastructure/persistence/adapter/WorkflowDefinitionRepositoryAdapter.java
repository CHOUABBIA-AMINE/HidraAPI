/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter for workflow definition repository port.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowDefinitionRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTypeReference;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowDefinitionJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowDefinitionJpaRepository;

/**
 * Persistence adapter for workflow definition repository port.
 *
 * <p>Note:
 * WF-014 introduced the definition repository only. Step and transition child-table persistence is
 * mapped by {@link WorkflowPersistenceMapper} and can be wired to dedicated child repositories when
 * they are introduced.
 */
@Repository
public class WorkflowDefinitionRepositoryAdapter implements WorkflowDefinitionRepositoryPort {

    private final WorkflowDefinitionJpaRepository definitionRepository;
    private final WorkflowPersistenceMapper mapper;

    public WorkflowDefinitionRepositoryAdapter(
            WorkflowDefinitionJpaRepository definitionRepository,
            WorkflowPersistenceMapper mapper) {

        this.definitionRepository = Objects.requireNonNull(definitionRepository, "WorkflowDefinitionJpaRepository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "WorkflowPersistenceMapper must not be null.");
    }

    @Override
    public WorkflowDefinition save(WorkflowDefinition definition) {
        Objects.requireNonNull(definition, "Workflow definition must not be null.");

        WorkflowDefinitionJpaEntity saved = definitionRepository.save(mapper.toEntity(definition));
        return mapper.toDomain(saved, List.of(), List.of());
    }

    @Override
    public Optional<WorkflowDefinition> findById(WorkflowDefinitionId id) {
        Objects.requireNonNull(id, "Workflow definition id must not be null.");

        return definitionRepository.findById(id.value())
                .map(entity -> mapper.toDomain(entity, List.of(), List.of()));
    }

    @Override
    public Optional<WorkflowDefinition> findByCode(WorkflowCode code) {
        Objects.requireNonNull(code, "Workflow definition code must not be null.");

        return definitionRepository.findByCode(code.value())
                .map(entity -> mapper.toDomain(entity, List.of(), List.of()));
    }

    @Override
    public boolean existsByCode(WorkflowCode code) {
        Objects.requireNonNull(code, "Workflow definition code must not be null.");
        return definitionRepository.existsByCode(code.value());
    }

    @Override
    public PageResult<WorkflowDefinition> findAll(
            String searchTerm,
            WorkflowTypeReference type,
            WorkflowDefinitionStatus status,
            PageRequest pageRequest) {

        Pageable pageable = WorkflowSpringPageables.from(pageRequest);
        Page<WorkflowDefinitionJpaEntity> page;

        if (searchTerm != null && !searchTerm.isBlank()) {
            page = definitionRepository.findByCodeContainingIgnoreCase(searchTerm.trim(), pageable);
        } else if (type != null && status != null) {
            page = definitionRepository.findByTypeIdAndStatus(type.id(), status.name(), pageable);
        } else if (type != null) {
            page = definitionRepository.findByTypeId(type.id(), pageable);
        } else if (status != null) {
            page = definitionRepository.findByStatus(status.name(), pageable);
        } else {
            page = definitionRepository.findAll(pageable);
        }

        return WorkflowSpringPageables.toPageResult(
                page,
                entity -> mapper.toDomain(entity, List.of(), List.of()));
    }
}
