/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyTransferTicketApprovalEvidenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 * @Description : Fences and resolves independently approved exact Custody ticket Workflow evidence.
 */
package dz.sh.hidra.modules.workflow.application.service;
import dz.sh.hidra.modules.workflow.application.contract.custody.CustodyTransferTicketApprovalEvidenceContract;
import dz.sh.hidra.modules.workflow.application.port.out.*;
import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import dz.sh.hidra.modules.workflow.domain.value.*;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
@Service
public class CustodyTransferTicketApprovalEvidenceQueryService implements CustodyTransferTicketApprovalEvidenceContract {
    private final WorkflowInstanceRepositoryPort instances;
    private final WorkflowTaskRepositoryPort tasks;
    private final WorkflowActionRepositoryPort actions;
    private final WorkflowConfigurationPort configuration;
    private final RevisionApprovalEvidenceQueryService.ManufacturerWorkflowFencePort fence;
    private final WorkflowTransitionApplicationService transitions;
    public CustodyTransferTicketApprovalEvidenceQueryService(WorkflowInstanceRepositoryPort instances,
            WorkflowTaskRepositoryPort tasks,WorkflowActionRepositoryPort actions,
            WorkflowConfigurationPort configuration,
            RevisionApprovalEvidenceQueryService.ManufacturerWorkflowFencePort fence,
            WorkflowTransitionApplicationService transitions){
        this.instances=Objects.requireNonNull(instances);this.tasks=Objects.requireNonNull(tasks);
        this.actions=Objects.requireNonNull(actions);this.configuration=Objects.requireNonNull(configuration);
        this.fence=Objects.requireNonNull(fence);this.transitions=Objects.requireNonNull(transitions);
    }
    private static boolean blank(String value){return value==null||value.isBlank();}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Evidence> resolve(Request r){
        if(r==null||r.evaluatedAt()==null||blank(r.ticketId())||blank(r.issuerId())||blank(r.reconcilerId())
                ||blank(r.instanceId())||blank(r.taskId())||blank(r.actionId())
                ||r.approvalTargetDigest()==null||!r.approvalTargetDigest().matches("[0-9a-f]{64}")
                ||r.reconciliationDigest()==null||!r.reconciliationDigest().matches("[0-9a-f]{64}"))
            return Optional.empty();
        // Workflow owns this physical fence. The originating Custody write transaction retains it
        // until a ticket finalization is durably committed or rolled back.
        fence.lockCurrentManufacturerApprovalEvidence();
        var i=instances.findById(r.instanceId()).orElse(null);
        var task=tasks.findById(r.taskId()).orElse(null);
        var action=actions.findById(r.actionId()).orElse(null);
        if(i==null||task==null||action==null||!i.id().equals(r.instanceId())
                ||!r.ticketId().equals(i.targetCodeSnapshot())
                ||!r.approvalTargetDigest().equals(i.targetId())
                ||!"custody".equals(i.targetModule())||i.status()!=WorkflowInstanceStatus.COMPLETED
                ||i.definitionVersion()<1||!r.issuerId().equals(i.startedByActorId())
                ||!i.id().equals(task.instanceId())||!task.id().equals(action.taskId())
                ||!i.id().equals(action.instanceId())||!task.id().equals(r.taskId())
                ||!action.id().equals(r.actionId())||task.status()!=WorkflowTaskStatus.APPROVED
                ||action.actionType()!=WorkflowActionType.APPROVE||action.decision()!=WorkflowDecision.APPROVE
                ||blank(action.actorId())||blank(action.actorDisplayNameSnapshot())
                ||action.actedAt().isBefore(i.startedAt())
                ||!action.actorId().equals(task.completedByActorId())
                ||!Objects.equals(action.actedAt(),task.completedAt())
                ||!Objects.equals(action.actedAt(),i.completedAt())||action.actedAt().isAfter(r.evaluatedAt())
                ||action.actionSequence()!=actions.nextSequence(i.id())-1
                ||action.actorId().equals(r.issuerId())||action.actorId().equals(r.reconcilerId())
                ||!transitions.eligibleIndependentHumanReviewer(action.actorId(),r.issuerId())
                ||!transitions.eligibleIndependentHumanReviewer(action.actorId(),r.reconcilerId()))
            return Optional.empty();
        try{
            var type=configuration.requireActiveCatalog(i.targetTypeId(),"WORKFLOW_TARGET_TYPE");
            var purpose=configuration.requireActiveCatalog(i.workflowPurposeId(),"WORKFLOW_PURPOSE");
            if(!type.active()||!purpose.active()||!"CUSTODY_TRANSFER_TICKET".equals(type.code())
                ||!"CUSTODY_TRANSFER_TICKET_APPROVAL".equals(purpose.code())
                ||!i.targetTypeId().equals(type.id())||!i.workflowPurposeId().equals(purpose.id())
                ||!configuration.activeBinding(i.definitionId(),"custody",type.id(),purpose.id()))
                return Optional.empty();
        }catch(InvalidWorkflowValueException notEligible){return Optional.empty();}
        return Optional.of(new Evidence(i.id(),task.id(),action.id(),action.actorId(),
            action.actorDisplayNameSnapshot(),i.definitionId(),i.definitionVersion(),
            i.targetTypeId(),i.workflowPurposeId(),action.actedAt()));
    }
}
