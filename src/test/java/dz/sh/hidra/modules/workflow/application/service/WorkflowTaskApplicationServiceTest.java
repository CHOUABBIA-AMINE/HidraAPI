/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Application service tests for workflow tasks.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.workflow.application.command.ApproveWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.ClaimWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.CommentWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.RequestWorkflowCorrectionCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowCommentDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTaskDto;
import dz.sh.hidra.modules.workflow.application.query.GetWorkflowTaskQuery;
import dz.sh.hidra.modules.workflow.application.query.ListMyWorkflowTasksQuery;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowAssignmentPolicy;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowDecisionPolicy;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowTransitionPolicy;
import dz.sh.hidra.modules.workflow.domain.service.WorkflowDecisionDomainService;
import dz.sh.hidra.modules.workflow.domain.service.WorkflowTaskDomainService;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCommentText;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCorrelationId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecisionNote;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;

/**
 * Application service tests for workflow tasks.
 */
class WorkflowTaskApplicationServiceTest {

    @Test
    void claimWorkflowTaskPersistsClaimedTaskAndPublishesAuditEvent() {
        TestContext context = new TestContext();

        WorkflowTaskDto claimed = context.service.claimWorkflowTask(new ClaimWorkflowTaskCommand(
                context.task.id(),
                WorkflowApplicationServiceTestFixtures.secondActor(),
                WorkflowCorrelationId.of("corr-claim")));

        assertEquals(WorkflowTaskStatus.CLAIMED.name(), claimed.status());
        assertEquals(1, context.auditEventPort.taskChangedCount());
    }

    @Test
    void approveWorkflowTaskRecordsActionClosesTaskAndPublishesAuditEvents() {
        TestContext context = new TestContext();

        WorkflowTaskDto approved = context.service.approveWorkflowTask(new ApproveWorkflowTaskCommand(
                context.instance.id(),
                context.task.id(),
                WorkflowApplicationServiceTestFixtures.actor(),
                WorkflowApplicationServiceTestFixtures.organization(),
                null,
                WorkflowDecisionNote.of("Accepted"),
                null,
                WorkflowCorrelationId.of("corr-approve")));

        assertEquals(WorkflowTaskStatus.APPROVED.name(), approved.status());
        assertEquals(1, context.auditEventPort.actionCount());
        assertEquals(1, context.auditEventPort.taskChangedCount());
    }

    @Test
    void requestCorrectionRecordsActionButKeepsTaskOpenForCorrection() {
        TestContext context = new TestContext();

        WorkflowTaskDto task = context.service.requestWorkflowCorrection(new RequestWorkflowCorrectionCommand(
                context.instance.id(),
                context.task.id(),
                WorkflowApplicationServiceTestFixtures.actor(),
                WorkflowApplicationServiceTestFixtures.organization(),
                WorkflowApplicationServiceTestFixtures.reason(),
                WorkflowCommentText.of("Please correct the source value."),
                WorkflowCorrelationId.of("corr-correction")));

        assertEquals(WorkflowTaskStatus.OPEN.name(), task.status());
        assertEquals(1, context.auditEventPort.actionCount());
    }

    @Test
    void commentWorkflowTaskReturnsCommentDtoAndRecordsAuditAction() {
        TestContext context = new TestContext();

        WorkflowCommentDto comment = context.service.commentWorkflowTask(new CommentWorkflowTaskCommand(
                context.instance.id(),
                context.task.id(),
                WorkflowApplicationServiceTestFixtures.actor(),
                WorkflowApplicationServiceTestFixtures.organization(),
                WorkflowCommentText.of("Reading was verified."),
                WorkflowCorrelationId.of("corr-comment")));

        assertEquals("Reading was verified.", comment.text());
        assertEquals(context.task.id().value(), comment.taskId());
        assertEquals(1, context.auditEventPort.actionCount());
    }

    @Test
    void getAndListMyTasksReturnRepositoryBackedDtoProjection() {
        TestContext context = new TestContext();

        WorkflowTaskDto task = context.service.getWorkflowTask(new GetWorkflowTaskQuery(context.task.id()));
        var page = context.service.listMyWorkflowTasks(new ListMyWorkflowTasksQuery(
                WorkflowApplicationServiceTestFixtures.actor(),
                null,
                null,
                WorkflowApplicationServiceTestFixtures.pageRequest()));

        assertEquals(context.task.id().value(), task.id());
        assertEquals(1, page.items().size());
    }

    private static final class TestContext {

        private final WorkflowApplicationServiceTestFixtures.InMemoryInstanceRepository instanceRepository =
                new WorkflowApplicationServiceTestFixtures.InMemoryInstanceRepository();
        private final WorkflowApplicationServiceTestFixtures.InMemoryTaskRepository taskRepository =
                new WorkflowApplicationServiceTestFixtures.InMemoryTaskRepository();
        private final WorkflowApplicationServiceTestFixtures.RecordingAuditEventPort auditEventPort =
                new WorkflowApplicationServiceTestFixtures.RecordingAuditEventPort();

        private final WorkflowDefinition definition = WorkflowApplicationServiceTestFixtures.activeDefinition();
        private final WorkflowInstance instance = WorkflowApplicationServiceTestFixtures.startedInstance(definition);
        private final WorkflowTask task = WorkflowApplicationServiceTestFixtures.openTask(instance);

        private final WorkflowTaskApplicationService service = new WorkflowTaskApplicationService(
                instanceRepository,
                taskRepository,
                auditEventPort,
                new WorkflowTaskDomainService(new WorkflowAssignmentPolicy()),
                new WorkflowDecisionDomainService(
                        new WorkflowDecisionPolicy(),
                        new WorkflowTransitionPolicy()));

        private TestContext() {
            instanceRepository.save(instance);
            taskRepository.save(task);
        }
    }
}
