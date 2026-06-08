/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Application service tests for workflow instances.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.application.command.CancelWorkflowInstanceCommand;
import dz.sh.hidra.modules.workflow.application.command.StartWorkflowInstanceCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowInstanceDto;
import dz.sh.hidra.modules.workflow.application.query.GetWorkflowTimelineQuery;
import dz.sh.hidra.modules.workflow.application.query.ListWorkflowInstancesQuery;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowDefinitionPolicy;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowTargetPolicy;
import dz.sh.hidra.modules.workflow.domain.service.WorkflowInstanceDomainService;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecisionNote;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId;

/**
 * Application service tests for workflow instances.
 */
class WorkflowInstanceApplicationServiceTest {

    @Test
    void startWorkflowInstanceResolvesTargetPersistsInstanceAndPublishesAuditEvent() {
        TestContext context = new TestContext();
        WorkflowDefinition definition = WorkflowApplicationServiceTestFixtures.activeDefinition();
        context.definitionRepository.save(definition);

        WorkflowInstanceDto dto = context.service.startWorkflowInstance(new StartWorkflowInstanceCommand(
                definition.id(),
                WorkflowApplicationServiceTestFixtures.telemetryReadingTarget(),
                WorkflowApplicationServiceTestFixtures.actor(),
                WorkflowCorrelationId.of("corr-start")));

        assertEquals(definition.id().value(), dto.definitionId());
        assertEquals("STARTED", dto.status());
        assertEquals("corr-start", dto.correlationId());
        assertEquals(1, context.auditEventPort.startedCount());
    }

    @Test
    void startWorkflowInstanceRejectsTargetThatCannotStartWorkflow() {
        TestContext context = new TestContext();
        WorkflowDefinition definition = WorkflowApplicationServiceTestFixtures.activeDefinition();
        context.definitionRepository.save(definition);
        context.targetLookupPort.denyStart();

        assertThrows(
                BusinessRuleViolationException.class,
                () -> context.service.startWorkflowInstance(new StartWorkflowInstanceCommand(
                        definition.id(),
                        WorkflowApplicationServiceTestFixtures.telemetryReadingTarget(),
                        WorkflowApplicationServiceTestFixtures.actor(),
                        WorkflowCorrelationId.of("corr-denied"))));
    }

    @Test
    void cancelWorkflowInstancePersistsCancelledState() {
        TestContext context = new TestContext();
        WorkflowDefinition definition = WorkflowApplicationServiceTestFixtures.activeDefinition();
        context.definitionRepository.save(definition);

        WorkflowInstanceDto started = context.service.startWorkflowInstance(new StartWorkflowInstanceCommand(
                definition.id(),
                WorkflowApplicationServiceTestFixtures.telemetryReadingTarget(),
                WorkflowApplicationServiceTestFixtures.actor(),
                null));

        WorkflowInstanceDto cancelled = context.service.cancelWorkflowInstance(new CancelWorkflowInstanceCommand(
                WorkflowInstanceId.of(started.id()),
                WorkflowApplicationServiceTestFixtures.actor(),
                WorkflowApplicationServiceTestFixtures.reason(),
                WorkflowDecisionNote.of("Cancelled by supervisor"),
                null));

        assertEquals("CANCELLED", cancelled.status());
    }

    @Test
    void listAndTimelineReturnDtoProjectionsFromRepository() {
        TestContext context = new TestContext();
        WorkflowDefinition definition = WorkflowApplicationServiceTestFixtures.activeDefinition();
        context.definitionRepository.save(definition);

        WorkflowInstanceDto started = context.service.startWorkflowInstance(new StartWorkflowInstanceCommand(
                definition.id(),
                WorkflowApplicationServiceTestFixtures.telemetryReadingTarget(),
                WorkflowApplicationServiceTestFixtures.actor(),
                null));

        var page = context.service.listWorkflowInstances(new ListWorkflowInstancesQuery(
                definition.id(),
                null,
                null,
                null,
                WorkflowApplicationServiceTestFixtures.pageRequest()));

        var timeline = context.service.getWorkflowTimeline(new GetWorkflowTimelineQuery(WorkflowInstanceId.of(started.id())));

        assertEquals(1, page.items().size());
        assertEquals(started.id(), timeline.instanceId());
    }

    private static final class TestContext {

        private final WorkflowApplicationServiceTestFixtures.InMemoryDefinitionRepository definitionRepository =
                new WorkflowApplicationServiceTestFixtures.InMemoryDefinitionRepository();
        private final WorkflowApplicationServiceTestFixtures.InMemoryInstanceRepository instanceRepository =
                new WorkflowApplicationServiceTestFixtures.InMemoryInstanceRepository();
        private final WorkflowApplicationServiceTestFixtures.FixedTargetLookupPort targetLookupPort =
                new WorkflowApplicationServiceTestFixtures.FixedTargetLookupPort();
        private final WorkflowApplicationServiceTestFixtures.RecordingAuditEventPort auditEventPort =
                new WorkflowApplicationServiceTestFixtures.RecordingAuditEventPort();

        private final WorkflowInstanceApplicationService service = new WorkflowInstanceApplicationService(
                definitionRepository,
                instanceRepository,
                targetLookupPort,
                auditEventPort,
                new WorkflowInstanceDomainService(
                        new WorkflowDefinitionPolicy(),
                        new WorkflowTargetPolicy()));
    }
}
