/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RevisionApprovalEvidenceQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Rejects prior approvals and mismatched actual Workflow identity, actor, time and configuration.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import dz.sh.hidra.modules.workflow.application.contract.target.RevisionApprovalEvidenceContract;
import dz.sh.hidra.modules.workflow.application.port.out.*;
import dz.sh.hidra.modules.workflow.domain.model.*;
import dz.sh.hidra.modules.workflow.domain.value.*;
import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

public class RevisionApprovalEvidenceQueryServiceTest {
    public static final Instant AT = Instant.parse("2020-01-01T00:01:00Z");
    public static final String DIGEST = "a".repeat(64);
    public static WorkflowInstance instance() { return new WorkflowInstance("instance", "def", 1, "purpose", "custody", "type", DIGEST, null, null, WorkflowInstanceStatus.COMPLETED, null, "actor", null, "Synthetic Reviewer", null, AT.minusSeconds(10), AT, null, null, AT.minusSeconds(10), AT); }
    public static WorkflowTask task() { return new WorkflowTask("task", "instance", "step", WorkflowTaskStatus.APPROVED, null, null, null, null, null, null, null, null, null, null, "actor", AT, null, null, null, null, null, null, AT.minusSeconds(10), AT); }
    public static WorkflowAction action() { return new WorkflowAction("action", "instance", "task", WorkflowActionType.APPROVE, WorkflowDecision.APPROVE, null, null, null, "actor", null, "Synthetic Reviewer", null, null, null, null, null, 1L, null, null, null, AT); }

    WorkflowInstanceRepositoryPort instances = mock(WorkflowInstanceRepositoryPort.class);
    WorkflowTaskRepositoryPort tasks = mock(WorkflowTaskRepositoryPort.class);
    WorkflowActionRepositoryPort actions = mock(WorkflowActionRepositoryPort.class);
    WorkflowConfigurationPort config = mock(WorkflowConfigurationPort.class);
    RevisionApprovalEvidenceQueryService service = new RevisionApprovalEvidenceQueryService(instances, tasks, actions, config);
    RevisionApprovalEvidenceContract.Request request() { return new RevisionApprovalEvidenceContract.Request("custody", DIGEST,
            "def", 1, "type", "purpose", "instance", "task", "action", AT); }
    @BeforeEach void setup() {
        when(instances.findById("instance")).thenReturn(Optional.of(instance()));
        when(tasks.findById("task")).thenReturn(Optional.of(task()));
        when(actions.findById("action")).thenReturn(Optional.of(action()));
        when(actions.nextSequence("instance")).thenReturn(2L);
        when(config.requireActiveCatalog("type", "WORKFLOW_TARGET_TYPE")).thenReturn(new WorkflowConfigurationPort.Catalog("type", "WORKFLOW_TARGET_TYPE", "GAS_FLUID_REVISION", true));
        when(config.requireActiveCatalog("purpose", "WORKFLOW_PURPOSE")).thenReturn(new WorkflowConfigurationPort.Catalog("purpose", "WORKFLOW_PURPOSE", "GAS_FLUID_INPUT_QUALIFICATION", true));
        when(config.activeBinding("def", "custody", "type", "purpose")).thenReturn(true);
    }
    @Test void readsActualExactFinalEvidence() {
        var a = service.resolve(request()).orElseThrow(); assertEquals("actor", a.actorId());
        assertEquals(AT, a.actedAt()); assertEquals("action", a.actionId());
    }
    @Test void rejectsEarlierApproveAndWithdrawnGovernance() {
        when(actions.nextSequence("instance")).thenReturn(3L); assertTrue(service.resolve(request()).isEmpty());
        setup(); when(config.activeBinding(any(), any(), any(), any())).thenReturn(false); assertTrue(service.resolve(request()).isEmpty());
        setup(); when(config.requireActiveCatalog("type", "WORKFLOW_TARGET_TYPE")).thenThrow(new InvalidWorkflowValueException("inactive"));
        assertTrue(service.resolve(request()).isEmpty());
    }
    @Test void propagatesInfrastructureFailures() {
        when(instances.findById(any())).thenThrow(new IllegalStateException("database unavailable"));
        assertThrows(IllegalStateException.class, () -> service.resolve(request()));
    }
    @Test void rejectsWorkflowInstanceTargetIdMismatch() {
        var v = instance(); var changed = new WorkflowInstance(v.id(), v.definitionId(), v.definitionVersion(), v.workflowPurposeId(), v.targetModule(), v.targetTypeId(), "b".repeat(64), v.targetCodeSnapshot(), v.targetLabelSnapshot(), v.status(), v.currentStepId(), v.startedByActorId(), v.startedByUsernameSnapshot(), v.startedByDisplayNameSnapshot(), v.startedByRoleCodeSnapshot(), v.startedAt(), v.completedAt(), v.cancelledAt(), v.correlationId(), v.createdAt(), v.updatedAt());
        when(instances.findById("instance")).thenReturn(Optional.of(changed)); assertTrue(service.resolve(request()).isEmpty());
    }
    @Test void rejectsWorkflowInstanceTargetModuleMismatch() {
        var v = instance(); var changed = new WorkflowInstance(v.id(), v.definitionId(), v.definitionVersion(), v.workflowPurposeId(), "simulation", v.targetTypeId(), v.targetId(), v.targetCodeSnapshot(), v.targetLabelSnapshot(), v.status(), v.currentStepId(), v.startedByActorId(), v.startedByUsernameSnapshot(), v.startedByDisplayNameSnapshot(), v.startedByRoleCodeSnapshot(), v.startedAt(), v.completedAt(), v.cancelledAt(), v.correlationId(), v.createdAt(), v.updatedAt());
        when(instances.findById("instance")).thenReturn(Optional.of(changed)); assertTrue(service.resolve(request()).isEmpty());
    }
    @Test void rejectsWorkflowInstanceDefinitionVersionMismatch() {
        var v = instance(); var changed = new WorkflowInstance(v.id(), v.definitionId(), 2, v.workflowPurposeId(), v.targetModule(), v.targetTypeId(), v.targetId(), v.targetCodeSnapshot(), v.targetLabelSnapshot(), v.status(), v.currentStepId(), v.startedByActorId(), v.startedByUsernameSnapshot(), v.startedByDisplayNameSnapshot(), v.startedByRoleCodeSnapshot(), v.startedAt(), v.completedAt(), v.cancelledAt(), v.correlationId(), v.createdAt(), v.updatedAt());
        when(instances.findById("instance")).thenReturn(Optional.of(changed)); assertTrue(service.resolve(request()).isEmpty());
    }
    @Test void rejectsWorkflowInstanceTargetTypeIdMismatch() {
        var v = instance(); var changed = new WorkflowInstance(v.id(), v.definitionId(), v.definitionVersion(), v.workflowPurposeId(), v.targetModule(), "other", v.targetId(), v.targetCodeSnapshot(), v.targetLabelSnapshot(), v.status(), v.currentStepId(), v.startedByActorId(), v.startedByUsernameSnapshot(), v.startedByDisplayNameSnapshot(), v.startedByRoleCodeSnapshot(), v.startedAt(), v.completedAt(), v.cancelledAt(), v.correlationId(), v.createdAt(), v.updatedAt());
        when(instances.findById("instance")).thenReturn(Optional.of(changed)); assertTrue(service.resolve(request()).isEmpty());
    }
    @Test void rejectsWorkflowInstanceWorkflowPurposeIdMismatch() {
        var v = instance(); var changed = new WorkflowInstance(v.id(), v.definitionId(), v.definitionVersion(), "other", v.targetModule(), v.targetTypeId(), v.targetId(), v.targetCodeSnapshot(), v.targetLabelSnapshot(), v.status(), v.currentStepId(), v.startedByActorId(), v.startedByUsernameSnapshot(), v.startedByDisplayNameSnapshot(), v.startedByRoleCodeSnapshot(), v.startedAt(), v.completedAt(), v.cancelledAt(), v.correlationId(), v.createdAt(), v.updatedAt());
        when(instances.findById("instance")).thenReturn(Optional.of(changed)); assertTrue(service.resolve(request()).isEmpty());
    }
    @Test void rejectsWorkflowInstanceStatusMismatch() {
        var v = instance(); var changed = new WorkflowInstance(v.id(), v.definitionId(), v.definitionVersion(), v.workflowPurposeId(), v.targetModule(), v.targetTypeId(), v.targetId(), v.targetCodeSnapshot(), v.targetLabelSnapshot(), WorkflowInstanceStatus.STARTED, v.currentStepId(), v.startedByActorId(), v.startedByUsernameSnapshot(), v.startedByDisplayNameSnapshot(), v.startedByRoleCodeSnapshot(), v.startedAt(), v.completedAt(), v.cancelledAt(), v.correlationId(), v.createdAt(), v.updatedAt());
        when(instances.findById("instance")).thenReturn(Optional.of(changed)); assertTrue(service.resolve(request()).isEmpty());
    }
    @Test void rejectsWorkflowInstanceCompletedAtMismatch() {
        var v = instance(); var changed = new WorkflowInstance(v.id(), v.definitionId(), v.definitionVersion(), v.workflowPurposeId(), v.targetModule(), v.targetTypeId(), v.targetId(), v.targetCodeSnapshot(), v.targetLabelSnapshot(), v.status(), v.currentStepId(), v.startedByActorId(), v.startedByUsernameSnapshot(), v.startedByDisplayNameSnapshot(), v.startedByRoleCodeSnapshot(), v.startedAt(), AT.minusSeconds(1), v.cancelledAt(), v.correlationId(), v.createdAt(), v.updatedAt());
        when(instances.findById("instance")).thenReturn(Optional.of(changed)); assertTrue(service.resolve(request()).isEmpty());
    }
    @Test void rejectsWorkflowTaskInstanceIdMismatch() {
        var v = task(); var changed = new WorkflowTask(v.id(), "other", v.stepId(), v.status(), v.assignedActorId(), v.assignedActorUsernameSnapshot(), v.assignedActorDisplayNameSnapshot(), v.assignedOrganizationUnitId(), v.assignedOrganizationUnitNameSnapshot(), v.assignedRoleCodeSnapshot(), v.priorityId(), v.dueAt(), v.claimedByActorId(), v.claimedAt(), v.completedByActorId(), v.completedAt(), v.assignmentModeId(), v.taskLabelSnapshot(), v.slaStatus(), v.escalatedAt(), v.delegatedAt(), v.expiresAt(), v.createdAt(), v.updatedAt());
        when(tasks.findById("task")).thenReturn(Optional.of(changed)); assertTrue(service.resolve(request()).isEmpty());
    }
    @Test void rejectsWorkflowTaskStatusMismatch() {
        var v = task(); var changed = new WorkflowTask(v.id(), v.instanceId(), v.stepId(), WorkflowTaskStatus.REJECTED, v.assignedActorId(), v.assignedActorUsernameSnapshot(), v.assignedActorDisplayNameSnapshot(), v.assignedOrganizationUnitId(), v.assignedOrganizationUnitNameSnapshot(), v.assignedRoleCodeSnapshot(), v.priorityId(), v.dueAt(), v.claimedByActorId(), v.claimedAt(), v.completedByActorId(), v.completedAt(), v.assignmentModeId(), v.taskLabelSnapshot(), v.slaStatus(), v.escalatedAt(), v.delegatedAt(), v.expiresAt(), v.createdAt(), v.updatedAt());
        when(tasks.findById("task")).thenReturn(Optional.of(changed)); assertTrue(service.resolve(request()).isEmpty());
    }
    @Test void rejectsWorkflowTaskCompletedByActorIdMismatch() {
        var v = task(); var changed = new WorkflowTask(v.id(), v.instanceId(), v.stepId(), v.status(), v.assignedActorId(), v.assignedActorUsernameSnapshot(), v.assignedActorDisplayNameSnapshot(), v.assignedOrganizationUnitId(), v.assignedOrganizationUnitNameSnapshot(), v.assignedRoleCodeSnapshot(), v.priorityId(), v.dueAt(), v.claimedByActorId(), v.claimedAt(), "other", v.completedAt(), v.assignmentModeId(), v.taskLabelSnapshot(), v.slaStatus(), v.escalatedAt(), v.delegatedAt(), v.expiresAt(), v.createdAt(), v.updatedAt());
        when(tasks.findById("task")).thenReturn(Optional.of(changed)); assertTrue(service.resolve(request()).isEmpty());
    }
    @Test void rejectsWorkflowTaskCompletedAtMismatch() {
        var v = task(); var changed = new WorkflowTask(v.id(), v.instanceId(), v.stepId(), v.status(), v.assignedActorId(), v.assignedActorUsernameSnapshot(), v.assignedActorDisplayNameSnapshot(), v.assignedOrganizationUnitId(), v.assignedOrganizationUnitNameSnapshot(), v.assignedRoleCodeSnapshot(), v.priorityId(), v.dueAt(), v.claimedByActorId(), v.claimedAt(), v.completedByActorId(), AT.minusSeconds(1), v.assignmentModeId(), v.taskLabelSnapshot(), v.slaStatus(), v.escalatedAt(), v.delegatedAt(), v.expiresAt(), v.createdAt(), v.updatedAt());
        when(tasks.findById("task")).thenReturn(Optional.of(changed)); assertTrue(service.resolve(request()).isEmpty());
    }
    @Test void rejectsWorkflowActionInstanceIdMismatch() {
        var v = action(); var changed = new WorkflowAction(v.id(), "other", v.taskId(), v.actionType(), v.decision(), v.reasonId(), v.decisionNote(), v.commentText(), v.actorId(), v.actorUsernameSnapshot(), v.actorDisplayNameSnapshot(), v.actorRoleCodeSnapshot(), v.organizationUnitId(), v.organizationUnitNameSnapshot(), v.organizationRoleCodeSnapshot(), v.correlationId(), v.actionSequence(), v.sourceSystem(), v.ipAddressHash(), v.userAgentHash(), v.actedAt());
        when(actions.findById("action")).thenReturn(Optional.of(changed)); assertTrue(service.resolve(request()).isEmpty());
    }
    @Test void rejectsWorkflowActionTaskIdMismatch() {
        var v = action(); var changed = new WorkflowAction(v.id(), v.instanceId(), "other", v.actionType(), v.decision(), v.reasonId(), v.decisionNote(), v.commentText(), v.actorId(), v.actorUsernameSnapshot(), v.actorDisplayNameSnapshot(), v.actorRoleCodeSnapshot(), v.organizationUnitId(), v.organizationUnitNameSnapshot(), v.organizationRoleCodeSnapshot(), v.correlationId(), v.actionSequence(), v.sourceSystem(), v.ipAddressHash(), v.userAgentHash(), v.actedAt());
        when(actions.findById("action")).thenReturn(Optional.of(changed)); assertTrue(service.resolve(request()).isEmpty());
    }
    @Test void rejectsWorkflowActionActorIdMismatch() {
        var v = action(); var changed = new WorkflowAction(v.id(), v.instanceId(), v.taskId(), v.actionType(), v.decision(), v.reasonId(), v.decisionNote(), v.commentText(), "other", v.actorUsernameSnapshot(), v.actorDisplayNameSnapshot(), v.actorRoleCodeSnapshot(), v.organizationUnitId(), v.organizationUnitNameSnapshot(), v.organizationRoleCodeSnapshot(), v.correlationId(), v.actionSequence(), v.sourceSystem(), v.ipAddressHash(), v.userAgentHash(), v.actedAt());
        when(actions.findById("action")).thenReturn(Optional.of(changed)); assertTrue(service.resolve(request()).isEmpty());
    }
    @Test void rejectsWorkflowActionActedAtMismatch() {
        var v = action(); var changed = new WorkflowAction(v.id(), v.instanceId(), v.taskId(), v.actionType(), v.decision(), v.reasonId(), v.decisionNote(), v.commentText(), v.actorId(), v.actorUsernameSnapshot(), v.actorDisplayNameSnapshot(), v.actorRoleCodeSnapshot(), v.organizationUnitId(), v.organizationUnitNameSnapshot(), v.organizationRoleCodeSnapshot(), v.correlationId(), v.actionSequence(), v.sourceSystem(), v.ipAddressHash(), v.userAgentHash(), AT.plusSeconds(1));
        when(actions.findById("action")).thenReturn(Optional.of(changed)); assertTrue(service.resolve(request()).isEmpty());
    }
    @Test void rejectsWorkflowActionActionTypeMismatch() {
        var v = action(); var changed = new WorkflowAction(v.id(), v.instanceId(), v.taskId(), WorkflowActionType.COMMENT, null, v.reasonId(), v.decisionNote(), v.commentText(), v.actorId(), v.actorUsernameSnapshot(), v.actorDisplayNameSnapshot(), v.actorRoleCodeSnapshot(), v.organizationUnitId(), v.organizationUnitNameSnapshot(), v.organizationRoleCodeSnapshot(), v.correlationId(), v.actionSequence(), v.sourceSystem(), v.ipAddressHash(), v.userAgentHash(), v.actedAt());
        when(actions.findById("action")).thenReturn(Optional.of(changed)); assertTrue(service.resolve(request()).isEmpty());
    }
}
