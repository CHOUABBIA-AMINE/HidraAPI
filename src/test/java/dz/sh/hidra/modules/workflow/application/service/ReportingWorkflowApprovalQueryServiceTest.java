/**
 * @Project : HidraAPI
 * @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author : Abir MEDJERAB
 * @Owner : Sonatrach / TRC : Digitalization Initiative
 * @Name : ReportingWorkflowApprovalQueryServiceTest
 * @CreatedOn : 2025-06-26
 * @UpdatedOn : 2026-10-05
 * @Type : Class
 * @Layer : Workflow Test
 * @Module : workflow
 * @Package : dz.sh.hidra.modules.workflow.application.service
 */
package dz.sh.hidra.modules.workflow.application.service;

import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReportingWorkflowApprovalQueryServiceTest {

    @Test
    void requiresCompletedReportingWorkflowWithApproveDecision() {
        WorkflowQueryUseCase query = mock(WorkflowQueryUseCase.class);
        when(query.instance("workflow-1")).thenReturn(new WorkflowQueryUseCase.InstanceView(
                "workflow-1",
                "definition-1",
                1,
                "purpose-1",
                "reporting",
                "REPORT_REQUEST",
                "request-1",
                "REQ-1",
                "Request 1",
                "COMPLETED",
                null,
                "actor-1",
                "user",
                "Actor One",
                Instant.parse("2026-10-05T00:00:00Z"),
                Instant.parse("2026-10-05T00:01:00Z"),
                null,
                "corr-1"
        ));
        when(query.timeline("workflow-1")).thenReturn(List.of(
                new WorkflowQueryUseCase.TimelineEntry(
                        "action-1",
                        "workflow-1",
                        "task-1",
                        "DECISION",
                        "APPROVE",
                        "actor-2",
                        "Approver",
                        null,
                        null,
                        1L,
                        Instant.parse("2026-10-05T00:01:00Z")
                )
        ));

        ReportingWorkflowApprovalQueryService service =
                new ReportingWorkflowApprovalQueryService(query);

        assertThat(service.approved("workflow-1", "request-1")).isTrue();
    }
}
