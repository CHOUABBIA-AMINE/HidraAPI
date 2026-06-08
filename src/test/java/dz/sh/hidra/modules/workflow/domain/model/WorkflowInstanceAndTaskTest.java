/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceAndTaskTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Unit tests for workflow instance and task domain models.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.domain.WorkflowDomainTestFixtures;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowStepId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;

/**
 * Unit tests for workflow instance and task domain models.
 */
class WorkflowInstanceAndTaskTest {

    @Test
    void startInstanceCreatesStartedInstanceAtInitialStep() {
        WorkflowInstance instance = WorkflowDomainTestFixtures.startedInstance();

        assertEquals(WorkflowInstanceStatus.STARTED, instance.status());
        assertNotNull(instance.id());
        assertNotNull(instance.currentStepId());
        assertEquals("corr-001", instance.correlationId().value());
    }

    @Test
    void addTaskRequiresTaskToBelongToInstance() {
        WorkflowInstance instance = WorkflowDomainTestFixtures.startedInstance();

        WorkflowTask foreignTask = WorkflowTask.open(
                WorkflowInstanceId.of("foreign-instance"),
                instance.currentStepId(),
                WorkflowDomainTestFixtures.actor(),
                null,
                WorkflowDomainTestFixtures.priority(),
                null);

        assertThrows(BusinessRuleViolationException.class, () -> instance.addTask(foreignTask));
    }

    @Test
    void taskCanBeClaimedAndCompletedOnce() {
        WorkflowInstance instance = WorkflowDomainTestFixtures.startedInstance();
        WorkflowTask task = WorkflowDomainTestFixtures.openTask(instance);

        WorkflowTask claimed = task.claim(WorkflowDomainTestFixtures.secondActor());
        WorkflowTask approved = claimed.complete(WorkflowDomainTestFixtures.secondActor(), WorkflowTaskStatus.APPROVED);

        assertEquals(WorkflowTaskStatus.CLAIMED, claimed.status());
        assertEquals(WorkflowTaskStatus.APPROVED, approved.status());
        assertNotNull(approved.completedAt());

        assertThrows(BusinessRuleViolationException.class, () -> approved.claim(WorkflowDomainTestFixtures.actor()));
    }

    @Test
    void taskCompletionStatusMustBeClosed() {
        WorkflowTask task = WorkflowDomainTestFixtures.openTask(WorkflowDomainTestFixtures.startedInstance());

        assertThrows(
                BusinessRuleViolationException.class,
                () -> task.complete(WorkflowDomainTestFixtures.actor(), WorkflowTaskStatus.IN_REVIEW));
    }

    @Test
    void moveToRecordsStateHistoryAndChangesStatus() {
        WorkflowInstance instance = WorkflowDomainTestFixtures.startedInstance();
        WorkflowStepId nextStepId = WorkflowStepId.of("next-step");

        WorkflowStateHistory history = WorkflowStateHistory.record(
                instance.id(),
                null,
                instance.currentStepId(),
                nextStepId,
                instance.status(),
                WorkflowInstanceStatus.IN_PROGRESS,
                WorkflowDomainTestFixtures.actor());

        WorkflowInstance moved = instance.moveTo(nextStepId, history);

        assertEquals(WorkflowInstanceStatus.IN_PROGRESS, moved.status());
        assertEquals(nextStepId, moved.currentStepId());
        assertEquals(1, moved.stateHistory().size());
    }

    @Test
    void completeTerminalInstanceIsIdempotent() {
        WorkflowInstance instance = WorkflowDomainTestFixtures.startedInstance();
        WorkflowInstance completed = instance.complete();

        assertEquals(WorkflowInstanceStatus.COMPLETED, completed.status());
        assertEquals(completed, completed.complete());
    }
}
