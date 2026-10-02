/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionWorkflowContractAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Verifies Alarm suppression accepts only completed matching Workflow approval evidence.
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

class AlarmSuppressionWorkflowContractAdapterTest {

    @Test
    void acceptsCompletedMatchingSuppressionWorkflow() {
        WorkflowQueryUseCase query = mock(WorkflowQueryUseCase.class);
        when(query.instance("wf-1")).thenReturn(instance("COMPLETED", "ALARM:alarm-1"));
        when(query.timeline("wf-1")).thenReturn(List.of(
                new WorkflowQueryUseCase.TimelineEntry(
                        "action-1", "wf-1", "task-1", "DECISION", "APPROVE",
                        "actor-1", "Operator", null, null, 4L,
                        Instant.parse("2026-10-02T09:00:00Z")
                )
        ));

        var adapter = new AlarmSuppressionWorkflowContractAdapter(query);
        var evidence = adapter.requireCompletedApproval("wf-1", "ALARM:alarm-1");

        assertThat(evidence.workflowInstanceId()).isEqualTo("wf-1");
        assertThat(evidence.workflowActionId()).isEqualTo("action-1");
        assertThat(evidence.decision()).isEqualTo("APPROVE");
    }

    @Test
    void rejectsWrongTargetPendingAndRejectedWorkflow() {
        WorkflowQueryUseCase query = mock(WorkflowQueryUseCase.class);
        var adapter = new AlarmSuppressionWorkflowContractAdapter(query);

        when(query.instance("wrong-target")).thenReturn(instance("COMPLETED", "ALARM:alarm-2"));
        assertThatThrownBy(() -> adapter.requireCompletedApproval("wrong-target", "ALARM:alarm-1"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("target");

        when(query.instance("pending")).thenReturn(instance("IN_PROGRESS", "ALARM:alarm-1"));
        assertThatThrownBy(() -> adapter.requireCompletedApproval("pending", "ALARM:alarm-1"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("not completed");

        when(query.instance("wf-1")).thenReturn(instance("COMPLETED", "ALARM:alarm-1"));
        when(query.timeline("wf-1")).thenReturn(List.of(
                new WorkflowQueryUseCase.TimelineEntry(
                        "action-1", "wf-1", "task-1", "DECISION", "REJECT",
                        "actor-1", "Operator", null, null, 5L,
                        Instant.parse("2026-10-02T09:00:00Z")
                )
        ));
        assertThatThrownBy(() -> adapter.requireCompletedApproval("wf-1", "ALARM:alarm-1"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("without approval");
    }

    private static WorkflowQueryUseCase.InstanceView instance(String status, String targetId) {
        String id = "COMPLETED".equals(status)
                ? ("ALARM:alarm-2".equals(targetId) ? "wrong-target" : "wf-1")
                : "pending";
        return new WorkflowQueryUseCase.InstanceView(
                id,
                "definition-1",
                1,
                "ALARM_SUPPRESSION_GOVERNANCE",
                "alarm",
                "ALARM_SUPPRESSION",
                targetId,
                null,
                null,
                status,
                null,
                "actor-1",
                "operator",
                "Operator",
                Instant.parse("2026-10-02T08:00:00Z"),
                "COMPLETED".equals(status) ? Instant.parse("2026-10-02T09:00:00Z") : null,
                null,
                "corr-1"
        );
    }
}
