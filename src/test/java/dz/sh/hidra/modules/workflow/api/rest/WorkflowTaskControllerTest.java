/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest
 *
 * @Description : Tests workflow task REST controller delegation.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.workflow.api.rest.controller.WorkflowTaskController;
import dz.sh.hidra.modules.workflow.api.rest.mapper.WorkflowRestMapper;
import dz.sh.hidra.modules.workflow.application.command.ClaimWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.port.in.ApproveWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.AssignWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ClaimWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.CommentWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.DelegateWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.EscalateWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListMyWorkflowTasksUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListWorkflowTasksUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.RejectWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.RequestWorkflowCorrectionUseCase;

/**
 * Tests workflow task REST controller delegation.
 */
class WorkflowTaskControllerTest {

    @Test
    void claimTaskDelegatesMappedCommandAndReturnsTaskResponse() {
        AtomicReference<Object> captured = new AtomicReference<>();
        WorkflowTaskController controller = controller(
                WorkflowRestApiTestFixtures.recordingUseCase(
                        ClaimWorkflowTaskUseCase.class,
                        WorkflowRestApiTestFixtures.taskDto(),
                        captured));

        var body = controller.claimTask("task-001", WorkflowRestApiTestFixtures.claimTaskRequest()).getBody();

        assertInstanceOf(ClaimWorkflowTaskCommand.class, captured.get());
        assertEquals("task-001", ((ClaimWorkflowTaskCommand) captured.get()).taskId().value());
        assertEquals("task-001", body.id());
    }

    @Test
    void decisionAndCommentEndpointsReturnMappedResponses() {
        WorkflowTaskController controller = controller(
                WorkflowRestApiTestFixtures.fixedUseCase(ClaimWorkflowTaskUseCase.class, WorkflowRestApiTestFixtures.taskDto()));

        assertEquals("task-001", controller.approveTask(
                "instance-001",
                "task-001",
                WorkflowRestApiTestFixtures.approveTaskRequest()).getBody().id());

        assertEquals("task-001", controller.requestCorrection(
                "instance-001",
                "task-001",
                WorkflowRestApiTestFixtures.correctionRequest()).getBody().id());

        assertEquals("comment-001", controller.commentTask(
                "instance-001",
                "task-001",
                WorkflowRestApiTestFixtures.commentRequest()).getBody().id());
    }

    @Test
    void listMyTasksReturnsMappedPageResponse() {
        WorkflowTaskController controller = controller(
                WorkflowRestApiTestFixtures.fixedUseCase(ClaimWorkflowTaskUseCase.class, WorkflowRestApiTestFixtures.taskDto()));

        var body = controller.listMyTasks(
                "actor-001",
                "a.medjerab",
                "Abir MEDJERAB",
                "SUPERVISOR",
                "org-trc",
                "TRC",
                "VALIDATOR",
                "OPEN",
                0,
                20,
                "dueAt",
                "ASC").getBody();

        assertEquals(1, body.items().size());
        assertEquals("task-001", body.items().get(0).id());
    }

    private WorkflowTaskController controller(ClaimWorkflowTaskUseCase claimUseCase) {
        return new WorkflowTaskController(
                WorkflowRestApiTestFixtures.fixedUseCase(AssignWorkflowTaskUseCase.class, WorkflowRestApiTestFixtures.taskDto()),
                claimUseCase,
                WorkflowRestApiTestFixtures.fixedUseCase(ApproveWorkflowTaskUseCase.class, WorkflowRestApiTestFixtures.taskDto()),
                WorkflowRestApiTestFixtures.fixedUseCase(RejectWorkflowTaskUseCase.class, WorkflowRestApiTestFixtures.taskDto()),
                WorkflowRestApiTestFixtures.fixedUseCase(RequestWorkflowCorrectionUseCase.class, WorkflowRestApiTestFixtures.taskDto()),
                WorkflowRestApiTestFixtures.fixedUseCase(DelegateWorkflowTaskUseCase.class, WorkflowRestApiTestFixtures.taskDto()),
                WorkflowRestApiTestFixtures.fixedUseCase(EscalateWorkflowTaskUseCase.class, WorkflowRestApiTestFixtures.taskDto()),
                WorkflowRestApiTestFixtures.fixedUseCase(CommentWorkflowTaskUseCase.class, WorkflowRestApiTestFixtures.commentDto()),
                WorkflowRestApiTestFixtures.fixedUseCase(GetWorkflowTaskUseCase.class, WorkflowRestApiTestFixtures.taskDto()),
                WorkflowRestApiTestFixtures.fixedUseCase(ListWorkflowTasksUseCase.class, WorkflowRestApiTestFixtures.taskPage()),
                WorkflowRestApiTestFixtures.fixedUseCase(ListMyWorkflowTasksUseCase.class, WorkflowRestApiTestFixtures.taskPage()),
                new WorkflowRestMapper());
    }
}
