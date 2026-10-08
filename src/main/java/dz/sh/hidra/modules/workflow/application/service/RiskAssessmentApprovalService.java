/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentApprovalService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import dz.sh.hidra.modules.workflow.application.contract.risk.RiskAssessmentApprovalContract;
import dz.sh.hidra.modules.workflow.application.port.out.*;
import dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.command.ExecuteWorkflowTransitionCommand;
import dz.sh.hidra.modules.workflow.domain.value.*;
import dz.sh.hidra.modules.identity.application.contract.risk.RiskActorContract;
import java.time.Instant;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RiskAssessmentApprovalService implements RiskAssessmentApprovalContract {
    private final WorkflowInstanceRepositoryPort instances;
    private final WorkflowTaskRepositoryPort tasks;
    private final WorkflowActionRepositoryPort actions;
    private final WorkflowConfigurationPort configuration;
    private final ExecuteWorkflowTransitionUseCase transitions;
    private final RiskActorContract actors;
    public RiskAssessmentApprovalService(WorkflowInstanceRepositoryPort instances,
            WorkflowTaskRepositoryPort tasks, WorkflowActionRepositoryPort actions,
            WorkflowConfigurationPort configuration, ExecuteWorkflowTransitionUseCase transitions,
            RiskActorContract actors) {
        this.instances=instances; this.tasks=tasks; this.actions=actions;
        this.configuration=configuration; this.transitions=transitions; this.actors=actors;
    }
    @Override @Transactional
    public Approval approve(Request request) {
        var actor=actors.currentActor(Instant.now());
        var task=tasks.findByIdForUpdate(request.taskId()).orElseThrow();
        var instance=instances.findByIdForUpdate(request.instanceId()).orElseThrow();
        var type=configuration.requireActiveCatalog(instance.targetTypeId(), "WORKFLOW_TARGET_TYPE");
        if (!"risk".equals(instance.targetModule()) || !"RISK_ASSESSMENT".equals(type.code())
                || !request.assessmentId().equals(instance.targetId()))
            throw new IllegalArgumentException("Workflow must target the exact Risk assessment.");
        var review=actions.findById(request.reviewActionId()).orElseThrow();
        if (!instance.id().equals(task.instanceId()) || !instance.id().equals(review.instanceId())
                || review.decision()!=WorkflowDecision.APPROVE || review.actedAt()==null
                || review.actorDisplayNameSnapshot()==null || review.actorDisplayNameSnapshot().isBlank())
            throw new IllegalArgumentException("An actual prior configured approval action is required as review evidence.");
        var result=transitions.execute(new ExecuteWorkflowTransitionCommand(request.taskId(), request.transitionId(),
                request.expectedTaskUpdatedAt(), request.reasonId(), request.decisionNote(), request.commentText(),
                request.correlationId(), actor.id(), actor.username(), actor.displayName(), Set.of()));
        if (!task.id().equals(result.taskId()) || !instance.id().equals(result.instanceId()) || !WorkflowDecision.APPROVE.name().equals(result.decision())
                || !WorkflowInstanceStatus.COMPLETED.name().equals(result.instanceStatus()) || result.executedAt()==null)
            throw new IllegalArgumentException("The configured final Workflow decision must approve this assessment.");
        var action=actions.findById(result.actionId()).orElseThrow();
        if (!task.id().equals(action.taskId()) || !instance.id().equals(action.instanceId()) || !actor.id().equals(action.actorId())
                || action.decision()!=WorkflowDecision.APPROVE || !result.executedAt().equals(action.actedAt())
                || review.actionSequence()>=action.actionSequence())
            throw new IllegalArgumentException("Actual Workflow approval evidence is incoherent.");
        return new Approval(instance.id(),task.id(),action.id(),review.id(),review.actorId(),
                review.actorDisplayNameSnapshot(),actor.id(),actor.username(),actor.displayName(),action.actedAt());
    }
}
