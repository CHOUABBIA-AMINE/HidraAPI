/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter for workflow task repository port.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTaskRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTaskJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowTaskJpaRepository;

/**
 * Persistence adapter for workflow task repository port.
 */
@Repository
public class WorkflowTaskRepositoryAdapter implements WorkflowTaskRepositoryPort {

    private final WorkflowTaskJpaRepository taskRepository;
    private final WorkflowPersistenceMapper mapper;

    public WorkflowTaskRepositoryAdapter(
            WorkflowTaskJpaRepository taskRepository,
            WorkflowPersistenceMapper mapper) {

        this.taskRepository = Objects.requireNonNull(taskRepository, "WorkflowTaskJpaRepository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "WorkflowPersistenceMapper must not be null.");
    }

    @Override
    public WorkflowTask save(WorkflowTask task) {
        Objects.requireNonNull(task, "Workflow task must not be null.");

        WorkflowTaskJpaEntity saved = taskRepository.save(mapper.toEntity(task));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<WorkflowTask> findById(WorkflowTaskId id) {
        Objects.requireNonNull(id, "Workflow task id must not be null.");

        return taskRepository.findById(id.value())
                .map(mapper::toDomain);
    }

    @Override
    public PageResult<WorkflowTask> findAll(
            WorkflowInstanceId instanceId,
            WorkflowTaskStatus status,
            WorkflowActorReference assignedActor,
            WorkflowOrganizationReference assignedOrganization,
            PageRequest pageRequest) {

        Pageable pageable = WorkflowSpringPageables.from(pageRequest);
        Page<WorkflowTaskJpaEntity> page;

        if (instanceId != null && status != null) {
            page = taskRepository.findByInstanceIdAndStatus(instanceId.value(), status.name(), pageable);
        } else if (instanceId != null) {
            page = taskRepository.findByInstanceId(instanceId.value(), pageable);
        } else if (assignedActor != null && status != null) {
            page = taskRepository.findByAssignedActorIdAndStatus(assignedActor.actorId(), status.name(), pageable);
        } else if (assignedOrganization != null && status != null) {
            page = taskRepository.findByAssignedOrganizationUnitIdAndStatus(
                    assignedOrganization.organizationUnitId(),
                    status.name(),
                    pageable);
        } else if (assignedActor != null) {
            page = taskRepository.findByAssignedActorId(assignedActor.actorId(), pageable);
        } else if (assignedOrganization != null) {
            page = taskRepository.findByAssignedOrganizationUnitId(assignedOrganization.organizationUnitId(), pageable);
        } else if (status != null) {
            page = taskRepository.findByStatus(status.name(), pageable);
        } else {
            page = taskRepository.findAll(pageable);
        }

        return WorkflowSpringPageables.toPageResult(page, mapper::toDomain);
    }

    @Override
    public PageResult<WorkflowTask> findVisibleToActor(
            WorkflowActorReference actor,
            WorkflowOrganizationReference organization,
            WorkflowTaskStatus status,
            PageRequest pageRequest) {

        Pageable pageable = WorkflowSpringPageables.from(pageRequest);
        Page<WorkflowTaskJpaEntity> page;

        if (actor != null && status != null) {
            page = taskRepository.findByAssignedActorIdAndStatus(actor.actorId(), status.name(), pageable);
        } else if (actor != null) {
            page = taskRepository.findByAssignedActorId(actor.actorId(), pageable);
        } else if (organization != null && status != null) {
            page = taskRepository.findByAssignedOrganizationUnitIdAndStatus(
                    organization.organizationUnitId(),
                    status.name(),
                    pageable);
        } else if (organization != null) {
            page = taskRepository.findByAssignedOrganizationUnitId(organization.organizationUnitId(), pageable);
        } else if (status != null) {
            page = taskRepository.findByStatus(status.name(), pageable);
        } else {
            page = taskRepository.findAll(pageable);
        }

        return WorkflowSpringPageables.toPageResult(page, mapper::toDomain);
    }
}
