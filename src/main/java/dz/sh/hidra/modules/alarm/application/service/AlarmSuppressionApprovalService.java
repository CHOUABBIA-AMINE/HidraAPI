/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionApprovalService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.service
 *
 * @Description : Verifies Workflow approval for open-ended alarm suppression commands.
 *
 */
package dz.sh.hidra.modules.alarm.application.service;

import dz.sh.hidra.modules.alarm.application.command.CreateAlarmSuppressionCommand;
import dz.sh.hidra.modules.workflow.application.contract.alarm.AlarmSuppressionWorkflowContract;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Application-layer approval gate for open-ended suppression.
 */
@Service
public final class AlarmSuppressionApprovalService {

    private final AlarmSuppressionWorkflowContract workflow;

    public AlarmSuppressionApprovalService(AlarmSuppressionWorkflowContract workflow) {
        this.workflow = Objects.requireNonNull(
                workflow,
                "Alarm suppression workflow contract must not be null."
        );
    }

    /**
     * Time-bounded suppression needs no Workflow approval. Open-ended suppression
     * must present matching completed APPROVE evidence or this method fails closed.
     */
    public Optional<AlarmSuppressionWorkflowContract.ApprovalEvidence> requireApprovalIfOpenEnded(
            CreateAlarmSuppressionCommand command
    ) {
        Objects.requireNonNull(command, "Create alarm suppression command must not be null.");
        if (command.suppressedUntil() != null) {
            return Optional.empty();
        }

        String operationReference = operationReference(command);
        return Optional.of(workflow.requireCompletedApproval(
                command.workflowInstanceId(),
                operationReference
        ));
    }

    static String operationReference(CreateAlarmSuppressionCommand command) {
        Objects.requireNonNull(command.scopeType(), "Suppression scope type must not be null.");
        String reference = command.scopeReferenceId();
        if (reference == null || reference.isBlank()) {
            throw new IllegalArgumentException("Suppression scope reference id must not be blank.");
        }
        return command.scopeType().name() + ":" + reference.trim();
    }
}
