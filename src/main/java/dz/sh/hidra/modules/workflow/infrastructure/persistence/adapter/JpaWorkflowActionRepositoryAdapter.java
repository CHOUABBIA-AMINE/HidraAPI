/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowActionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WorkflowAction.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowActionRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowAction;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActionType;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowActionJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowActionJpaRepository;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowInstanceJpaEntity;
import org.springframework.transaction.annotation.Transactional;
import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class JpaWorkflowActionRepositoryAdapter implements WorkflowActionRepositoryPort {

    private final WorkflowActionJpaRepository repository;
    private final EntityManager em;

    public JpaWorkflowActionRepositoryAdapter(WorkflowActionJpaRepository repository, EntityManager em) {
        this.em=Objects.requireNonNull(em);
        this.repository = Objects.requireNonNull(repository, "WorkflowActionJpaRepository must not be null.");
    }

    @Override
    @Transactional
    public WorkflowAction save(WorkflowAction model) {
        Objects.requireNonNull(model);
        if(em.find(WorkflowInstanceJpaEntity.class,model.instanceId(),LockModeType.PESSIMISTIC_WRITE)==null)
            throw new InvalidWorkflowValueException("Unknown Workflow action instance.");
        if(model.actionSequence()!=repository.nextSequence(model.instanceId()))
            throw new InvalidWorkflowValueException("Workflow action sequence must be the next server-owned instance sequence.");
        em.persist(WorkflowPersistenceMapper.toEntity(model));
        em.flush();
        return model;
    }

    @Override
    public Optional<WorkflowAction> findById(String id) {
        return repository.findById(id).map(WorkflowPersistenceMapper::toDomain);
    }

    @Override
    public List<WorkflowAction> findPressureApprovals(String candidateId, String submitterId) {
        return em.createQuery("""
                SELECT a FROM WorkflowActionJpaEntity a, WorkflowInstanceJpaEntity i
                WHERE a.instanceId = i.id AND i.targetModule = 'topology'
                  AND i.targetId = :candidate AND i.targetCodeSnapshot = :locator
                  AND i.startedByActorId = :submitter AND i.status = :completed
                  AND a.actionType = :approve AND a.taskId IS NOT NULL
                ORDER BY a.actedAt, a.id
                """, WorkflowActionJpaEntity.class)
                .setParameter("candidate", candidateId)
                .setParameter("locator", "t36-v1:" + candidateId)
                .setParameter("submitter", submitterId)
                .setParameter("completed", WorkflowInstanceStatus.COMPLETED)
                .setParameter("approve", WorkflowActionType.APPROVE)
                .getResultList().stream().map(WorkflowPersistenceMapper::toDomain).toList();
    }

    @Override
    public long nextSequence(String instanceId) {
        return repository.nextSequence(instanceId);
    }
}
