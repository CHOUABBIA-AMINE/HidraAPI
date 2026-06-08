/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowAction
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Workflow action domain entity.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActionType;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCommentText;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecisionNote;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowReasonReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskId;

/**
 * Workflow action domain entity.
 */
public final class WorkflowAction implements Entity<WorkflowActionId> {

    private final WorkflowActionId id;
    private final WorkflowInstanceId instanceId;
    private final WorkflowTaskId taskId;
    private final WorkflowActionType actionType;
    private final WorkflowDecision decision;
    private final WorkflowReasonReference reason;
    private final WorkflowDecisionNote decisionNote;
    private final WorkflowCommentText comment;
    private final WorkflowActorReference actor;
    private final WorkflowOrganizationReference organization;
    private final WorkflowCorrelationId correlationId;
    private final Instant actedAt;

    private WorkflowAction(
            WorkflowActionId id,
            WorkflowInstanceId instanceId,
            WorkflowTaskId taskId,
            WorkflowActionType actionType,
            WorkflowDecision decision,
            WorkflowReasonReference reason,
            WorkflowDecisionNote decisionNote,
            WorkflowCommentText comment,
            WorkflowActorReference actor,
            WorkflowOrganizationReference organization,
            WorkflowCorrelationId correlationId,
            Instant actedAt) {

        this.id = Objects.requireNonNull(id, "Workflow action id must not be null.");
        this.instanceId = Objects.requireNonNull(instanceId, "Workflow action instanceId must not be null.");
        this.taskId = taskId;
        this.actionType = Objects.requireNonNull(actionType, "Workflow action type must not be null.");
        this.decision = decision;
        this.reason = reason;
        this.decisionNote = decisionNote;
        this.comment = comment;
        this.actor = Objects.requireNonNull(actor, "Workflow action actor must not be null.");
        this.organization = organization;
        this.correlationId = correlationId;
        this.actedAt = Objects.requireNonNull(actedAt, "Workflow action actedAt must not be null.");

        if (decision != null && decision.requiresReason() && reason == null) {
            throw new BusinessRuleViolationException("Workflow decision requires a reason.");
        }
    }

    public static WorkflowAction record(
            WorkflowInstanceId instanceId,
            WorkflowTaskId taskId,
            WorkflowActionType actionType,
            WorkflowDecision decision,
            WorkflowReasonReference reason,
            WorkflowDecisionNote decisionNote,
            WorkflowCommentText comment,
            WorkflowActorReference actor,
            WorkflowOrganizationReference organization,
            WorkflowCorrelationId correlationId) {

        return new WorkflowAction(
                WorkflowActionId.newId(),
                instanceId,
                taskId,
                actionType,
                decision,
                reason,
                decisionNote,
                comment,
                actor,
                organization,
                correlationId,
                Instant.now());
    }

    public static WorkflowAction restore(
            WorkflowActionId id,
            WorkflowInstanceId instanceId,
            WorkflowTaskId taskId,
            WorkflowActionType actionType,
            WorkflowDecision decision,
            WorkflowReasonReference reason,
            WorkflowDecisionNote decisionNote,
            WorkflowCommentText comment,
            WorkflowActorReference actor,
            WorkflowOrganizationReference organization,
            WorkflowCorrelationId correlationId,
            Instant actedAt) {

        return new WorkflowAction(
                id,
                instanceId,
                taskId,
                actionType,
                decision,
                reason,
                decisionNote,
                comment,
                actor,
                organization,
                correlationId,
                actedAt);
    }

    @Override
    public WorkflowActionId id() {
        return id;
    }

    public WorkflowInstanceId instanceId() {
        return instanceId;
    }

    public WorkflowTaskId taskId() {
        return taskId;
    }

    public WorkflowActionType actionType() {
        return actionType;
    }

    public WorkflowDecision decision() {
        return decision;
    }

    public WorkflowReasonReference reason() {
        return reason;
    }

    public WorkflowDecisionNote decisionNote() {
        return decisionNote;
    }

    public WorkflowCommentText comment() {
        return comment;
    }

    public WorkflowActorReference actor() {
        return actor;
    }

    public WorkflowOrganizationReference organization() {
        return organization;
    }

    public WorkflowCorrelationId correlationId() {
        return correlationId;
    }

    public Instant actedAt() {
        return actedAt;
    }
}
