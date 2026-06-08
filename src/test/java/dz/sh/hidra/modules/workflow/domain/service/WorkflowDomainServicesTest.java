/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDomainServicesTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.service
 *
 * @Description : Unit tests for workflow domain services.
 *
 */
package dz.sh.hidra.modules.workflow.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.workflow.domain.WorkflowDomainTestFixtures;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowAction;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowAssignmentPolicy;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowDecisionPolicy;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowDefinitionPolicy;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowTargetPolicy;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowTransitionPolicy;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;

/**
 * Unit tests for workflow domain services.
 */
class WorkflowDomainServicesTest {

    @Test
    void definitionServiceCreatesStepsTransitionsAndActivatesDefinition() {
        WorkflowDefinitionDomainService service = new WorkflowDefinitionDomainService(
                new WorkflowDefinitionPolicy(),
                new WorkflowTransitionPolicy());

        WorkflowDefinition definition = service.createDefinition(
                dz.sh.hidra.modules.workflow.domain.value.WorkflowCode.of("TELEMETRY_VALIDATION"),
                WorkflowDomainTestFixtures.localizedName(),
                WorkflowDomainTestFixtures.workflowType());

        WorkflowDefinition withReview = service.addStep(
                definition,
                "REVIEW",
                WorkflowDomainTestFixtures.localizedName(),
                0,
                true);

        WorkflowDefinition withDone = service.addStep(
                withReview,
                "DONE",
                WorkflowDomainTestFixtures.localizedName(),
                1,
                true);

        WorkflowDefinition withTransition = service.addTransition(
                withDone,
                withDone.steps().get(0).id(),
                withDone.steps().get(1).id(),
                WorkflowDecision.APPROVE,
                false,
                false);

        WorkflowDefinition active = service.activate(withTransition);

        assertEquals(WorkflowDefinitionStatus.ACTIVE, active.status());
        assertEquals(2, active.steps().size());
        assertEquals(1, active.transitions().size());
    }

    @Test
    void instanceServiceStartsTelemetryReadingWorkflow() {
        WorkflowInstanceDomainService service = new WorkflowInstanceDomainService(
                new WorkflowDefinitionPolicy(),
                new WorkflowTargetPolicy());

        WorkflowDefinition definition = WorkflowDomainTestFixtures.activeDefinitionWithTransition();

        WorkflowInstance instance = service.startTelemetryReadingValidation(
                definition,
                WorkflowDomainTestFixtures.telemetryReadingTarget(),
                WorkflowDomainTestFixtures.actor(),
                dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId.of("corr-domain-service"));

        assertEquals(definition.id(), instance.definitionId());
        assertEquals(definition.firstStep().orElseThrow().id(), instance.currentStepId());
        assertEquals("corr-domain-service", instance.correlationId().value());
    }

    @Test
    void taskServiceClaimsAndApprovesTask() {
        WorkflowTaskDomainService service = new WorkflowTaskDomainService(new WorkflowAssignmentPolicy());
        WorkflowTask task = WorkflowDomainTestFixtures.openTask(WorkflowDomainTestFixtures.startedInstance());

        WorkflowTask claimed = service.claimTask(task, WorkflowDomainTestFixtures.secondActor());
        WorkflowTask approved = service.approveTask(claimed, WorkflowDomainTestFixtures.secondActor());

        assertEquals(WorkflowTaskStatus.CLAIMED, claimed.status());
        assertEquals(WorkflowTaskStatus.APPROVED, approved.status());
    }

    @Test
    void decisionServiceRecordsDecisionAction() {
        WorkflowDecisionDomainService service = new WorkflowDecisionDomainService(
                new WorkflowDecisionPolicy(),
                new WorkflowTransitionPolicy());

        WorkflowInstance instance = WorkflowDomainTestFixtures.startedInstance();
        WorkflowTask task = WorkflowDomainTestFixtures.openTask(instance);

        WorkflowAction action = service.recordDecision(
                instance,
                task,
                WorkflowDecision.APPROVE,
                null,
                dz.sh.hidra.modules.workflow.domain.value.WorkflowDecisionNote.of("Accepted"),
                null,
                WorkflowDomainTestFixtures.actor(),
                WorkflowDomainTestFixtures.organization(),
                dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId.of("corr-decision"));

        assertNotNull(action.id());
        assertEquals(WorkflowDecision.APPROVE, action.decision());
        assertEquals(task.id(), action.taskId());
    }
}
