/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentWorkflowQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Enforces Incident lifecycle integrity and owner-controlled evidence.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import dz.sh.hidra.modules.workflow.application.contract.incident.IncidentWorkflowContract;
import dz.sh.hidra.modules.workflow.application.port.out.*;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase;
import dz.sh.hidra.modules.workflow.domain.value.*;
import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import java.time.Instant;
import java.util.Comparator;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class IncidentWorkflowQueryService implements IncidentWorkflowContract {
    private final WorkflowInstanceRepositoryPort instances;
    private final WorkflowConfigurationPort configuration;
    private final WorkflowQueryUseCase query;
    private final WorkflowActionRepositoryPort actions;
    private final WorkflowTaskRepositoryPort tasks;
    private final WorkflowActorContract actors;
    public IncidentWorkflowQueryService(WorkflowInstanceRepositoryPort instances,WorkflowConfigurationPort configuration,
            WorkflowQueryUseCase query,WorkflowActionRepositoryPort actions,WorkflowTaskRepositoryPort tasks,WorkflowActorContract actors) {
        this.instances=Objects.requireNonNull(instances);this.configuration=Objects.requireNonNull(configuration);this.query=Objects.requireNonNull(query);
        this.actions=Objects.requireNonNull(actions);this.tasks=Objects.requireNonNull(tasks);this.actors=Objects.requireNonNull(actors);
    }
    public boolean exists(String id) {return id!=null && !id.isBlank() && instances.findById(id.trim()).isPresent();}
    @Transactional
    public boolean closureApproved(String id,String incidentId,String actorId,Instant at) {
        if(id==null || incidentId==null || actorId==null || at==null || actors.eligibleActor(actorId,at).isEmpty()) return false;
        var instance=instances.findByIdForUpdate(id).orElse(null);
        if(instance==null || instance.status()!=WorkflowInstanceStatus.COMPLETED || !"incident".equals(instance.targetModule())
                || !incidentId.equals(instance.targetId()) || instance.completedAt()==null || instance.completedAt().isAfter(at)) return false;
        var type=configuration.requireActiveCatalog(instance.targetTypeId(),"WORKFLOW_TARGET_TYPE");
        var purpose=configuration.requireActiveCatalog(instance.workflowPurposeId(),"WORKFLOW_PURPOSE");
        if(!"INCIDENT".equals(type.code()) || !"INCIDENT_CLOSURE".equals(purpose.code())
                || !configuration.activeBinding(instance.definitionId(),"incident",instance.targetTypeId(),instance.workflowPurposeId())) return false;
        var finalEntry=query.timeline(id).stream().max(Comparator.comparingLong(WorkflowQueryUseCase.TimelineEntry::sequence)).orElse(null);
        if(finalEntry==null) return false;
        var action=actions.findById(finalEntry.id()).orElse(null);
        if(action==null || action.taskId()==null || !id.equals(action.instanceId()) || action.decision()!=WorkflowDecision.APPROVE
                || action.actionType()!=WorkflowActionType.APPROVE || !actorId.equals(action.actorId())
                || action.actedAt()==null || action.actedAt().isAfter(at) || !action.actedAt().equals(instance.completedAt())) return false;
        var task=tasks.findById(action.taskId()).orElse(null);
        return task!=null && id.equals(task.instanceId()) && task.status()==WorkflowTaskStatus.APPROVED
                && actorId.equals(task.completedByActorId()) && action.actedAt().equals(task.completedAt());
    }
}
