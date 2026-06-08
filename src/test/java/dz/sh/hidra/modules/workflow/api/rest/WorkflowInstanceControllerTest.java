/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest
 *
 * @Description : Tests workflow instance REST controller delegation.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.workflow.api.rest.controller.WorkflowInstanceController;
import dz.sh.hidra.modules.workflow.api.rest.mapper.WorkflowRestMapper;
import dz.sh.hidra.modules.workflow.application.command.StartWorkflowInstanceCommand;
import dz.sh.hidra.modules.workflow.application.port.in.CancelWorkflowInstanceUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowInstanceUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowTimelineUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListWorkflowInstancesUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.StartWorkflowInstanceUseCase;

/**
 * Tests workflow instance REST controller delegation.
 */
class WorkflowInstanceControllerTest {

    @Test
    void startInstanceDelegatesMappedCommandAndReturnsResponse() {
        AtomicReference<Object> captured = new AtomicReference<>();

        WorkflowInstanceController controller = new WorkflowInstanceController(
                WorkflowRestApiTestFixtures.recordingUseCase(
                        StartWorkflowInstanceUseCase.class,
                        WorkflowRestApiTestFixtures.instanceDto(),
                        captured),
                WorkflowRestApiTestFixtures.fixedUseCase(CancelWorkflowInstanceUseCase.class, WorkflowRestApiTestFixtures.instanceDto()),
                WorkflowRestApiTestFixtures.fixedUseCase(GetWorkflowInstanceUseCase.class, WorkflowRestApiTestFixtures.instanceDto()),
                WorkflowRestApiTestFixtures.fixedUseCase(ListWorkflowInstancesUseCase.class, WorkflowRestApiTestFixtures.instancePage()),
                WorkflowRestApiTestFixtures.fixedUseCase(GetWorkflowTimelineUseCase.class, WorkflowRestApiTestFixtures.timelineDto()),
                new WorkflowRestMapper());

        var body = controller.startInstance(WorkflowRestApiTestFixtures.startInstanceRequest()).getBody();

        assertInstanceOf(StartWorkflowInstanceCommand.class, captured.get());
        assertEquals("definition-001", ((StartWorkflowInstanceCommand) captured.get()).definitionId().value());
        assertEquals("instance-001", body.id());
    }

    @Test
    void listAndTimelineEndpointsReturnMappedResponses() {
        WorkflowInstanceController controller = new WorkflowInstanceController(
                WorkflowRestApiTestFixtures.fixedUseCase(StartWorkflowInstanceUseCase.class, WorkflowRestApiTestFixtures.instanceDto()),
                WorkflowRestApiTestFixtures.fixedUseCase(CancelWorkflowInstanceUseCase.class, WorkflowRestApiTestFixtures.instanceDto()),
                WorkflowRestApiTestFixtures.fixedUseCase(GetWorkflowInstanceUseCase.class, WorkflowRestApiTestFixtures.instanceDto()),
                WorkflowRestApiTestFixtures.fixedUseCase(ListWorkflowInstancesUseCase.class, WorkflowRestApiTestFixtures.instancePage()),
                WorkflowRestApiTestFixtures.fixedUseCase(GetWorkflowTimelineUseCase.class, WorkflowRestApiTestFixtures.timelineDto()),
                new WorkflowRestMapper());

        assertEquals(1, controller.listInstances(
                "definition-001",
                "telemetry",
                "workflow-target-type-telemetry-reading",
                "TELEMETRY_READING",
                "reading-001",
                "actor-001",
                "a.medjerab",
                "Abir MEDJERAB",
                "SUPERVISOR",
                "STARTED",
                0,
                20,
                "startedAt",
                "DESC").getBody().items().size());

        assertEquals("instance-001", controller.getTimeline("instance-001").getBody().instanceId());
    }
}
