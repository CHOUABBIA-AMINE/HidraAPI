/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionApprovalServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.service
 *
 * @Description : Verifies Workflow approval is mandatory only for open-ended suppression.
 *
 */
package dz.sh.hidra.modules.alarm.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.alarm.application.command.CreateAlarmSuppressionCommand;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionScopeType;
import dz.sh.hidra.modules.workflow.application.contract.alarm.AlarmSuppressionWorkflowContract;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class AlarmSuppressionApprovalServiceTest {

    @Test
    void boundedSuppressionDoesNotInvokeWorkflow() {
        AlarmSuppressionWorkflowContract workflow = mock(AlarmSuppressionWorkflowContract.class);
        var service = new AlarmSuppressionApprovalService(workflow);

        assertThat(service.requireApprovalIfOpenEnded(command(
                Instant.parse("2026-10-02T10:00:00Z"), null
        ))).isEmpty();

        verifyNoInteractions(workflow);
    }

    @Test
    void openEndedSuppressionRequiresMatchingWorkflowApproval() {
        AlarmSuppressionWorkflowContract workflow = mock(AlarmSuppressionWorkflowContract.class);
        var evidence = new AlarmSuppressionWorkflowContract.ApprovalEvidence(
                "wf-1", "task-1", "action-1", "APPROVE",
                Instant.parse("2026-10-02T09:00:00Z")
        );
        when(workflow.requireCompletedApproval("wf-1", "ALARM:alarm-1")).thenReturn(evidence);

        var service = new AlarmSuppressionApprovalService(workflow);
        assertThat(service.requireApprovalIfOpenEnded(command(null, "wf-1")))
                .contains(evidence);

        verify(workflow).requireCompletedApproval("wf-1", "ALARM:alarm-1");
    }

    @Test
    void openEndedSuppressionFailsClosedWhenWorkflowRejectsEvidence() {
        AlarmSuppressionWorkflowContract workflow = mock(AlarmSuppressionWorkflowContract.class);
        when(workflow.requireCompletedApproval("wf-1", "ALARM:alarm-1"))
                .thenThrow(new IllegalStateException("approval missing"));

        var service = new AlarmSuppressionApprovalService(workflow);
        assertThatThrownBy(() -> service.requireApprovalIfOpenEnded(command(null, "wf-1")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("approval missing");
    }

    private static CreateAlarmSuppressionCommand command(Instant until, String workflowId) {
        return new CreateAlarmSuppressionCommand(
                AlarmSuppressionScopeType.ALARM,
                "alarm-1",
                "alarm-1",
                null,
                null,
                null,
                "reason-1",
                "maintenance",
                "actor-1",
                until,
                workflowId,
                "corr-1"
        );
    }
}
