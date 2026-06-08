/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter for workflow instance repository port.
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
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetReference;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowInstanceJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowActionJpaRepository;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowInstanceJpaRepository;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowTaskJpaRepository;

/**
 * Persistence adapter for workflow instance repository port.
 */
@Repository
public class WorkflowInstanceRepositoryAdapter implements WorkflowInstanceRepositoryPort {

    private static final List<String> TERMINAL_STATUSES = List.of("COMPLETED", "CANCELLED", "FAILED");

    private final WorkflowInstanceJpaRepository instanceRepository;
    private final WorkflowTaskJpaRepository taskRepository;
    private final WorkflowActionJpaRepository actionRepository;
    private final WorkflowPersistenceMapper mapper;

    public WorkflowInstanceRepositoryAdapter(
            WorkflowInstanceJpaRepository instanceRepository,
            WorkflowTaskJpaRepository taskRepository,
            WorkflowActionJpaRepository actionRepository,
            WorkflowPersistenceMapper mapper) {

        this.instanceRepository = Objects.requireNonNull(instanceRepository, "WorkflowInstanceJpaRepository must not be null.");
        this.taskRepository = Objects.requireNonNull(taskRepository, "WorkflowTaskJpaRepository must not be null.");
        this.actionRepository = Objects.requireNonNull(actionRepository, "WorkflowActionJpaRepository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "WorkflowPersistenceMapper must not be null.");
    }

    @Override
    public WorkflowInstance save(WorkflowInstance instance) {
        Objects.requireNonNull(instance, "Workflow instance must not be null.");

        WorkflowInstanceJpaEntity saved = instanceRepository.save(mapper.toEntity(instance));
        instance.actions().forEach(action -> actionRepository.save(mapper.toEntity(action)));

        return mapper.toDomain(
                saved,
                taskRepository.findByInstanceId(saved.getId(), Pageable.unpaged()).getContent(),
                actionRepository.findByInstanceIdOrderByActedAtAsc(saved.getId()),
                List.of());
    }

    @Override
    public Optional<WorkflowInstance> findById(WorkflowInstanceId id) {
        Objects.requireNonNull(id, "Workflow instance id must not be null.");

        return instanceRepository.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public Optional<WorkflowInstance> findOpenByTarget(WorkflowTargetReference target) {
        Objects.requireNonNull(target, "Workflow target must not be null.");

        return instanceRepository.findFirstByTargetModuleAndTargetTypeIdAndTargetIdAndStatusNotIn(
                        target.targetModule(),
                        target.targetType().id(),
                        target.targetId(),
                        TERMINAL_STATUSES)
                .map(this::toDomain);
    }

    @Override
    public PageResult<WorkflowInstance> findAll(
            WorkflowDefinitionId definitionId,
            WorkflowTargetReference target,
            WorkflowInstanceStatus status,
            WorkflowActorReference startedBy,
            PageRequest pageRequest) {

        Pageable pageable = WorkflowSpringPageables.from(pageRequest);
        Page<WorkflowInstanceJpaEntity> page;

        if (target != null) {
            page = instanceRepository.findByTargetModuleAndTargetTypeIdAndTargetId(
                    target.targetModule(),
                    target.targetType().id(),
                    target.targetId(),
                    pageable);
        } else if (definitionId != null) {
            page = instanceRepository.findByDefinitionId(definitionId.value(), pageable);
        } else if (status != null) {
            page = instanceRepository.findByStatus(status.name(), pageable);
        } else if (startedBy != null) {
            page = instanceRepository.findByStartedByActorId(startedBy.actorId(), pageable);
        } else {
            page = instanceRepository.findAll(pageable);
        }

        return WorkflowSpringPageables.toPageResult(page, this::toDomain);
    }

    private WorkflowInstance toDomain(WorkflowInstanceJpaEntity entity) {
        return mapper.toDomain(
                entity,
                taskRepository.findByInstanceId(entity.getId(), Pageable.unpaged()).getContent(),
                actionRepository.findByInstanceIdOrderByActedAtAsc(entity.getId()),
                List.of());
    }
}
