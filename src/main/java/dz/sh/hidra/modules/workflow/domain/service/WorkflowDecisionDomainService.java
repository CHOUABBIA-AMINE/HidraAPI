/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDecisionDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.service
 *
 * @Description : Domain service for workflow decision orchestration.
 *
 */
package dz.sh.hidra.modules.workflow.domain.service;

import java.util.Objects;

import dz.sh.hidra.modules.workflow.domain.model.WorkflowAction;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTransition;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowDecisionPolicy;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowTransitionPolicy;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActionType;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCommentText;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecisionNote;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowReasonReference;

/**
 * Domain service for workflow decision orchestration.
 *
 * <p>Business role:
 * Validates task decisions, resolves matching transitions, and records workflow actions.
 */
public final class WorkflowDecisionDomainService {

    private final WorkflowDecisionPolicy decisionPolicy;
    private final WorkflowTransitionPolicy transitionPolicy;

    public WorkflowDecisionDomainService(
            WorkflowDecisionPolicy decisionPolicy,
            WorkflowTransitionPolicy transitionPolicy) {

        this.decisionPolicy = Objects.requireNonNull(decisionPolicy, "WorkflowDecisionPolicy must not be null.");
        this.transitionPolicy = Objects.requireNonNull(transitionPolicy, "WorkflowTransitionPolicy must not be null.");
    }

    public WorkflowAction recordDecision(
            WorkflowInstance instance,
            WorkflowTask task,
            WorkflowDecision decision,
            WorkflowReasonReference reason,
            WorkflowDecisionNote decisionNote,
            WorkflowCommentText comment,
            WorkflowActorReference actor,
            WorkflowOrganizationReference organization,
            WorkflowCorrelationId correlationId) {

        Objects.requireNonNull(instance, "Workflow instance must not be null.");
        Objects.requireNonNull(task, "Workflow task must not be null.");
        Objects.requireNonNull(actor, "Workflow decision actor must not be null.");

        decisionPolicy.requireDecisionCanBeApplied(task, decision, reason, comment);

        return WorkflowAction.record(
                instance.id(),
                task.id(),
                actionTypeFor(decision),
                decision,
                reason,
                decisionNote,
                comment,
                actor,
                organization,
                correlationId);
    }

    public WorkflowTransition resolveTransition(
            WorkflowDefinition definition,
            WorkflowInstance instance,
            WorkflowDecision decision,
            WorkflowReasonReference reason,
            WorkflowCommentText comment) {

        Objects.requireNonNull(definition, "Workflow definition must not be null.");
        Objects.requireNonNull(instance, "Workflow instance must not be null.");

        return transitionPolicy.requireApplicableTransition(
                definition,
                instance.currentStepId(),
                decision,
                reason,
                comment);
    }

    public WorkflowAction recordComment(
            WorkflowInstance instance,
            WorkflowTask task,
            WorkflowCommentText comment,
            WorkflowActorReference actor,
            WorkflowOrganizationReference organization,
            WorkflowCorrelationId correlationId) {

        return recordDecision(
                instance,
                task,
                WorkflowDecision.COMMENT,
                null,
                null,
                comment,
                actor,
                organization,
                correlationId);
    }

    private WorkflowActionType actionTypeFor(WorkflowDecision decision) {
        Objects.requireNonNull(decision, "Workflow decision must not be null.");

        return switch (decision) {
            case APPROVE -> WorkflowActionType.APPROVE;
            case REJECT -> WorkflowActionType.REJECT;
            case REQUEST_CORRECTION -> WorkflowActionType.REQUEST_CORRECTION;
            case CORRECT -> WorkflowActionType.CORRECT;
            case RETURN -> WorkflowActionType.RETURN;
            case DELEGATE -> WorkflowActionType.DELEGATE;
            case ESCALATE -> WorkflowActionType.ESCALATE;
            case CANCEL -> WorkflowActionType.CANCEL;
            case COMMENT -> WorkflowActionType.COMMENT;
        };
    }
}
