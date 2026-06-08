/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDomainTestFixtures
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain
 *
 * @Description : Shared workflow domain test fixtures.
 *
 */
package dz.sh.hidra.modules.workflow.domain;

import java.time.Instant;

import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStep;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTransition;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCommentText;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDueDate;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowLocalizedName;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowPriorityReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowReasonReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTargetTypeReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTypeReference;

/**
 * Shared workflow domain test fixtures.
 */
public final class WorkflowDomainTestFixtures {

    private WorkflowDomainTestFixtures() {
        // Test utility class.
    }

    public static WorkflowLocalizedName localizedName() {
        return WorkflowLocalizedName.of("تحقق القياسات", "Validation télémétrie", "Telemetry validation");
    }

    public static WorkflowTypeReference workflowType() {
        return WorkflowTypeReference.of("workflow-type-telemetry-validation", "TELEMETRY_VALIDATION");
    }

    public static WorkflowTargetTypeReference telemetryReadingTargetType() {
        return WorkflowTargetTypeReference.of("workflow-target-type-telemetry-reading", "TELEMETRY_READING");
    }

    public static WorkflowTargetReference telemetryReadingTarget() {
        return WorkflowTargetReference.of(
                "telemetry",
                telemetryReadingTargetType(),
                "reading-001",
                "READING-001",
                "Telemetry reading reading-001");
    }

    public static WorkflowTargetReference nonTelemetryTarget() {
        return WorkflowTargetReference.of(
                "planning",
                WorkflowTargetTypeReference.of("workflow-target-type-plan-item", "PLAN_ITEM"),
                "plan-001",
                "PLAN-001",
                "Plan item");
    }

    public static WorkflowActorReference actor() {
        return WorkflowActorReference.of("actor-001", "a.medjerab", "Abir MEDJERAB", "SUPERVISOR");
    }

    public static WorkflowActorReference secondActor() {
        return WorkflowActorReference.of("actor-002", "m.operateur", "Mohammed Operateur", "OPERATOR");
    }

    public static WorkflowOrganizationReference organization() {
        return WorkflowOrganizationReference.of("org-trc", "TRC", "VALIDATOR");
    }

    public static WorkflowReasonReference reason() {
        return WorkflowReasonReference.of("workflow-reason-out-of-range", "OUT_OF_RANGE");
    }

    public static WorkflowPriorityReference priority() {
        return WorkflowPriorityReference.of("workflow-priority-normal", "NORMAL");
    }

    public static WorkflowCommentText comment() {
        return WorkflowCommentText.of("Please verify the telemetry reading value.");
    }

    public static WorkflowDefinition draftDefinition() {
        return WorkflowDefinition.create(
                WorkflowCode.of("TELEMETRY_READING_VALIDATION"),
                localizedName(),
                workflowType());
    }

    public static WorkflowDefinition definitionWithTwoSteps() {
        WorkflowDefinition definition = draftDefinition();

        WorkflowStep review = WorkflowStep.create(
                definition.id(),
                "SUPERVISOR_REVIEW",
                WorkflowLocalizedName.of(null, "Revue superviseur", "Supervisor review"),
                0,
                true);

        WorkflowStep completed = WorkflowStep.create(
                definition.id(),
                "COMPLETED",
                WorkflowLocalizedName.of(null, "Terminé", "Completed"),
                1,
                true);

        return definition.addStep(review).addStep(completed);
    }

    public static WorkflowDefinition activeDefinitionWithTransition() {
        WorkflowDefinition definition = definitionWithTwoSteps();
        WorkflowStep from = definition.steps().get(0);
        WorkflowStep to = definition.steps().get(1);

        WorkflowTransition transition = WorkflowTransition.create(
                definition.id(),
                from.id(),
                to.id(),
                dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision.APPROVE,
                false,
                false);

        return definition.addTransition(transition).activate();
    }

    public static WorkflowInstance startedInstance() {
        WorkflowDefinition definition = activeDefinitionWithTransition();

        return WorkflowInstance.start(
                definition.id(),
                definition.version(),
                telemetryReadingTarget(),
                definition.firstStep().orElseThrow().id(),
                actor(),
                WorkflowCorrelationId.of("corr-001"));
    }

    public static WorkflowTask openTask(WorkflowInstance instance) {
        return WorkflowTask.open(
                instance.id(),
                instance.currentStepId(),
                actor(),
                null,
                priority(),
                WorkflowDueDate.of(Instant.now().plusSeconds(3600)));
    }
}
