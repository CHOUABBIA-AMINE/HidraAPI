/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowStateHistoryRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for WorkflowStateHistory.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowStateHistoryRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStateHistory;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowStateHistoryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;
import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.*;
import java.util.Optional;

/**
 * Database-backed repository adapter for WorkflowStateHistory.
 */
@Component
public class JpaWorkflowStateHistoryRepositoryAdapter implements WorkflowStateHistoryRepositoryPort {

    private final WorkflowStateHistoryJpaRepository repository;
    private final EntityManager em;

    public JpaWorkflowStateHistoryRepositoryAdapter(WorkflowStateHistoryJpaRepository repository, EntityManager em) {
        this.em=Objects.requireNonNull(em);
        this.repository = Objects.requireNonNull(repository, "WorkflowStateHistoryJpaRepository must not be null.");
    }

    @Override
    @Transactional
    public WorkflowStateHistory save(WorkflowStateHistory model) {
        Objects.requireNonNull(model);
        var instance=em.find(WorkflowInstanceJpaEntity.class,model.instanceId());
        if(instance==null) throw invalid("Unknown Workflow history instance.");
        WorkflowTaskJpaEntity task=null;
        if(model.taskId()!=null){
            task=em.find(WorkflowTaskJpaEntity.class,model.taskId());
            if(task==null || !instance.id().equals(task.instanceId())) throw invalid("Workflow history task belongs to another instance.");
        }
        validateStep(model.fromStepId(),instance.definitionId());
        validateStep(model.toStepId(),instance.definitionId());
        if(task!=null && model.fromStepId()!=null && !task.stepId().equals(model.fromStepId())) throw invalid("Workflow history source step does not match its task.");
        if(model.actionId()!=null){
            var action=em.find(WorkflowActionJpaEntity.class,model.actionId());
            if(action==null || !instance.id().equals(action.instanceId()) || !model.actorId().equals(action.actorId())
                || (model.taskId()!=null && !model.taskId().equals(action.taskId()))
                || (model.reasonId()!=null && !model.reasonId().equals(action.reasonId()))) throw invalid("Workflow history action evidence is incoherent.");
            if(action.taskId()!=null && model.fromStepId()!=null){
                var actionTask=em.find(WorkflowTaskJpaEntity.class,action.taskId());
                if(actionTask==null || !model.fromStepId().equals(actionTask.stepId())) throw invalid("Workflow history source step does not match action task.");
            }
        }
        if(model.reasonId()!=null){
            var reason=em.find(WorkflowCatalogEntryJpaEntity.class,model.reasonId());
            if(reason==null || !reason.active() || !"WORKFLOW_REASON".equals(reason.catalogName())) throw invalid("Workflow history requires an active WORKFLOW_REASON.");
        }
        em.persist(WorkflowPersistenceMapper.toEntity(model));
        em.flush();
        return model;
    }

    @Override
    public Optional<WorkflowStateHistory> findById(String id) {
        return repository.findById(id).map(WorkflowPersistenceMapper::toDomain);
    }
    private void validateStep(String id,String definition){
        if(id==null)return;
        var step=em.find(WorkflowStepJpaEntity.class,id);
        if(step==null || !definition.equals(step.definitionId())) throw invalid("Workflow history step belongs to another definition.");
    }
    private static InvalidWorkflowValueException invalid(String message){return new InvalidWorkflowValueException(message);}
}
