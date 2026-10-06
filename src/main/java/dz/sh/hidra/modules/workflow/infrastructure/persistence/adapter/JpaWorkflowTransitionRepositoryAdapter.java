/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowTransitionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WorkflowTransition.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTransitionRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTransition;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowTransitionJpaRepository;
import java.util.Objects;
import jakarta.persistence.EntityManager;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowStepJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowDefinitionJpaEntity;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class JpaWorkflowTransitionRepositoryAdapter implements WorkflowTransitionRepositoryPort {

    private final WorkflowTransitionJpaRepository repository;
    private final EntityManager em;

    public JpaWorkflowTransitionRepositoryAdapter(WorkflowTransitionJpaRepository repository, EntityManager em) {
        this.em=Objects.requireNonNull(em);
        this.repository = Objects.requireNonNull(repository, "WorkflowTransitionJpaRepository must not be null.");
    }

    @Override
    public WorkflowTransition save(WorkflowTransition model) {
        Objects.requireNonNull(model);
        var from=em.find(WorkflowStepJpaEntity.class,model.fromStepId());
        var to=em.find(WorkflowStepJpaEntity.class,model.toStepId());
        var definition=em.find(WorkflowDefinitionJpaEntity.class,model.definitionId());
        if(definition==null || from==null || to==null || !model.definitionId().equals(from.definitionId())
                || !model.definitionId().equals(to.definitionId()))
            throw new InvalidWorkflowValueException("Workflow transition steps must belong to its definition.");
        if(definition.status()==WorkflowDefinitionStatus.ACTIVE && (model.conditionExpression()!=null
                || model.targetModuleCallback()!=null || model.decision()==dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision.COMMENT))
            throw new InvalidWorkflowValueException("Unsupported Workflow configuration cannot be attached to an ACTIVE definition.");
        if(em.createQuery("select count(e) from WorkflowTransitionJpaEntity e where e.definitionId=:definition and e.fromStepId=:step and e.decision=:decision and e.id<>:id",Long.class)
            .setParameter("definition",model.definitionId()).setParameter("step",model.fromStepId())
            .setParameter("decision",model.decision()).setParameter("id",model.id()).getSingleResult()>0L)
            throw new InvalidWorkflowValueException("Workflow decision is already configured for this source step.");
        return WorkflowPersistenceMapper.toDomain(repository.save(WorkflowPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<WorkflowTransition> findById(String id) {
        return repository.findById(id).map(WorkflowPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsFromStep(String definitionId, String fromStepId) {
        return repository.existsByDefinitionIdAndFromStepId(definitionId, fromStepId);
    }
}
