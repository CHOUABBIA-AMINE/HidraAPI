/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowEscalationDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.service
 *
 * @Description : Domain service for workflow escalation orchestration.
 *
 */
package dz.sh.hidra.modules.workflow.domain.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.modules.workflow.domain.model.WorkflowAction;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowEscalationRule;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowEscalationPolicy;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActionType;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecisionNote;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowReasonReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowStepId;

/**
 * Domain service for workflow escalation orchestration.
 *
 * <p>Business role:
 * Creates escalation rules and records escalation actions when overdue tasks match active rules.
 */
public final class WorkflowEscalationDomainService {

    private final WorkflowEscalationPolicy escalationPolicy;

    public WorkflowEscalationDomainService(WorkflowEscalationPolicy escalationPolicy) {
        this.escalationPolicy = Objects.requireNonNull(escalationPolicy, "WorkflowEscalationPolicy must not be null.");
    }

    public WorkflowEscalationRule createRule(
            WorkflowDefinitionId definitionId,
            WorkflowStepId stepId,
            Duration afterDuration,
            WorkflowActorReference escalateToActor,
            WorkflowOrganizationReference escalateToOrganization) {

        escalationPolicy.requirePositiveEscalationDuration(afterDuration);

        return WorkflowEscalationRule.create(
                definitionId,
                stepId,
                afterDuration,
                escalateToActor,
                escalateToOrganization);
    }

    public WorkflowAction recordEscalation(
            WorkflowTask task,
            WorkflowEscalationRule rule,
            WorkflowActorReference actor,
            WorkflowOrganizationReference organization,
            WorkflowReasonReference reason,
            WorkflowDecisionNote note,
            WorkflowCorrelationId correlationId,
            Instant referenceTime) {

        Objects.requireNonNull(task, "Workflow task must not be null.");
        Objects.requireNonNull(actor, "Workflow escalation actor must not be null.");
        Objects.requireNonNull(reason, "Workflow escalation reason must not be null.");

        escalationPolicy.requireTaskCanBeEscalated(task, rule, referenceTime);

        return WorkflowAction.record(
                task.instanceId(),
                task.id(),
                WorkflowActionType.ESCALATE,
                WorkflowDecision.ESCALATE,
                reason,
                note,
                null,
                actor,
                organization,
                correlationId);
    }
}
