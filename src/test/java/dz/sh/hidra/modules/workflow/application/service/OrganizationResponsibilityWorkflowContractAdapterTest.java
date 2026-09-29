/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationResponsibilityWorkflowContractAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Verifies Organization responsibility changes accept only completed matching Workflow evidence.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

class OrganizationResponsibilityWorkflowContractAdapterTest {

    @Test
    void acceptsCompletedMatchingResponsibilityWorkflow() {
        WorkflowQueryUseCase query = mock(WorkflowQueryUseCase.class);
        when(query.instance("wf-1")).thenReturn(instance("COMPLETED", "operation-1"));
        when(query.timeline("wf-1")).thenReturn(List.of(
                new WorkflowQueryUseCase.TimelineEntry(
                        "action-1",
                        "wf-1",
                        "task-1",
                        "DECISION",
                        "APPROVE",
                        "actor-1",
                        "Operator",
                        null,
                        null,
                        7L,
                        Instant.parse("2026-09-29T09:00:00Z")
                )
        ));

        var adapter = new OrganizationResponsibilityWorkflowContractAdapter(query);
        var evidence = adapter.requireCompletedApproval("wf-1", "operation-1");

        assertThat(evidence.workflowInstanceId()).isEqualTo("wf-1");
        assertThat(evidence.workflowTaskId()).isEqualTo("task-1");
        assertThat(evidence.workflowActionId()).isEqualTo("action-1");
        assertThat(evidence.decision()).isEqualTo("APPROVE");
    }

    @Test
    void rejectsWrongTargetAndNonCompletedWorkflow() {
        WorkflowQueryUseCase query = mock(WorkflowQueryUseCase.class);
        var adapter = new OrganizationResponsibilityWorkflowContractAdapter(query);

        when(query.instance("wrong-target")).thenReturn(instance("COMPLETED", "other-operation"));
        assertThatThrownBy(() -> adapter.requireCompletedApproval("wrong-target", "operation-1"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("target");

        when(query.instance("pending")).thenReturn(instance("ACTIVE", "operation-1"));
        assertThatThrownBy(() -> adapter.requireCompletedApproval("pending", "operation-1"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("not completed");
    }

    @Test
    void rejectsCompletedNonApprovalDecision() {
        WorkflowQueryUseCase query = mock(WorkflowQueryUseCase.class);
        when(query.instance("wf-1")).thenReturn(instance("COMPLETED", "operation-1"));
        when(query.timeline("wf-1")).thenReturn(List.of(
                new WorkflowQueryUseCase.TimelineEntry(
                        "action-1", "wf-1", "task-1", "DECISION", "REJECT",
                        "actor-1", "Operator", null, null, 8L,
                        Instant.parse("2026-09-29T09:00:00Z")
                )
        ));

        var adapter = new OrganizationResponsibilityWorkflowContractAdapter(query);
        assertThatThrownBy(() -> adapter.requireCompletedApproval("wf-1", "operation-1"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("without approval");
    }

    private static WorkflowQueryUseCase.InstanceView instance(String status, String targetId) {
        return new WorkflowQueryUseCase.InstanceView(
                status.equals("ACTIVE") ? "pending" : targetId.equals("other-operation") ? "wrong-target" : "wf-1",
                "definition-1",
                1,
                "RESPONSIBILITY_GOVERNANCE",
                "organization",
                "RESPONSIBILITY_CHANGE",
                targetId,
                null,
                null,
                status,
                null,
                "actor-1",
                "operator",
                "Operator",
                Instant.parse("2026-09-29T08:00:00Z"),
                status.equals("COMPLETED") ? Instant.parse("2026-09-29T09:00:00Z") : null,
                null,
                "correlation-1"
        );
    }
}
