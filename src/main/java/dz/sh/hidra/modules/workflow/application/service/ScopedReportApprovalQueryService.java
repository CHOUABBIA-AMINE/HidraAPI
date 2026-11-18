/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScopedReportApprovalQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.workflow.application.service;
import dz.sh.hidra.modules.workflow.application.contract.reporting.ScopedReportApprovalContract;
import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowActionRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowConfigurationPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTaskRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActionType;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScopedReportApprovalQueryService implements ScopedReportApprovalContract {
    private final WorkflowInstanceRepositoryPort instances;
    private final WorkflowActionRepositoryPort actions;
    private final WorkflowTaskRepositoryPort tasks;
    private final WorkflowConfigurationPort configuration;
    public ScopedReportApprovalQueryService(WorkflowInstanceRepositoryPort instances,
            WorkflowActionRepositoryPort actions, WorkflowTaskRepositoryPort tasks,
            WorkflowConfigurationPort configuration) {
        this.instances=instances; this.actions=actions; this.tasks=tasks; this.configuration=configuration;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Decision> approved(String requestId, String instanceId, String actionId) {
        var instance=instances.findByIdForUpdate(instanceId).orElse(null);
        var action=actions.findById(actionId).orElse(null);
        if (instance==null || action==null || !"reporting".equals(instance.targetModule())
                || !requestId.equals(instance.targetId()) || instance.status()!=WorkflowInstanceStatus.COMPLETED
                || !instance.id().equals(action.instanceId()) || action.actionType()!=WorkflowActionType.APPROVE
                || action.decision()!=WorkflowDecision.APPROVE || action.taskId()==null || action.actorId()==null
                || action.actorId().equals(instance.startedByActorId()) || !action.actedAt().equals(instance.completedAt()))
            return Optional.empty();
        var task=tasks.findById(action.taskId()).orElse(null);
        var type=configuration.requireActiveCatalog(instance.targetTypeId(),"WORKFLOW_TARGET_TYPE");
        var purpose=configuration.requireActiveCatalog(instance.workflowPurposeId(),"WORKFLOW_PURPOSE");
        if (task==null || !instance.id().equals(task.instanceId())
                || !WorkflowOwnedTargetLookup.scopedReportApproval(type.code(),purpose.code())
                || !configuration.activeBinding(instance.definitionId(),"reporting",
                        instance.targetTypeId(),instance.workflowPurposeId())) return Optional.empty();
        return Optional.of(new Decision(instanceId,actionId,action.actorId(),action.actedAt(),
                instance.definitionId(),instance.definitionVersion()));
    }
}
