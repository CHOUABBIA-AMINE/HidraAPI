/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionWorkflowContractAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Adapts Workflow query state to Alarm suppression approval evidence.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import dz.sh.hidra.modules.workflow.application.contract.alarm.AlarmSuppressionWorkflowContract;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.springframework.stereotype.Service;

/**
 * Requires a completed APPROVE workflow deliberately targeted to one Alarm suppression operation.
 */
@Service
public final class AlarmSuppressionWorkflowContractAdapter
        implements AlarmSuppressionWorkflowContract {

    private static final String ALARM_MODULE = "alarm";
    private static final String ALARM_SUPPRESSION = "ALARM_SUPPRESSION";
    private static final String COMPLETED = "COMPLETED";

    private final WorkflowQueryUseCase workflowQuery;

    public AlarmSuppressionWorkflowContractAdapter(WorkflowQueryUseCase workflowQuery) {
        this.workflowQuery = Objects.requireNonNull(
                workflowQuery,
                "Workflow query use case must not be null."
        );
    }

    @Override
    public ApprovalEvidence requireCompletedApproval(
            String workflowInstanceId,
            String operationReference
    ) {
        String instanceId = requireText(
                workflowInstanceId,
                "Open-ended alarm suppression requires a workflow instance."
        );
        String reference = requireText(
                operationReference,
                "Alarm suppression operation reference must not be blank."
        );

        WorkflowQueryUseCase.InstanceView instance = workflowQuery.instance(instanceId);
        if (!ALARM_MODULE.equalsIgnoreCase(normalize(instance.targetModule()))) {
            throw new IllegalStateException(
                    "Workflow instance does not target the alarm module: " + instance.id()
            );
        }
        if (!ALARM_SUPPRESSION.equals(normalizeState(instance.targetTypeId()))) {
            throw new IllegalStateException(
                    "Workflow instance is not an alarm-suppression approval: " + instance.id()
            );
        }
        if (!Objects.equals(reference, normalize(instance.targetId()))) {
            throw new IllegalStateException(
                    "Workflow instance target does not match the alarm suppression operation reference."
            );
        }
        if (!COMPLETED.equals(normalizeState(instance.status()))) {
            throw new IllegalStateException(
                    "Alarm suppression workflow approval is not completed: " + instance.id()
            );
        }

        List<WorkflowQueryUseCase.TimelineEntry> timeline = workflowQuery.timeline(instance.id());
        WorkflowQueryUseCase.TimelineEntry finalAction = timeline.stream()
                .max(Comparator.comparingLong(WorkflowQueryUseCase.TimelineEntry::sequence))
                .orElseThrow(() -> new IllegalStateException(
                        "Completed alarm suppression workflow has no action evidence: " + instance.id()
                ));

        String decision = requireText(
                finalAction.decision(),
                "Completed alarm suppression workflow has no decision evidence."
        );
        if (!"APPROVE".equals(normalizeState(decision))) {
            throw new IllegalStateException(
                    "Alarm suppression workflow completed without approval: " + instance.id()
            );
        }

        return new ApprovalEvidence(
                instance.id(),
                finalAction.taskId(),
                finalAction.id(),
                decision,
                finalAction.occurredAt()
        );
    }

    private static String normalizeState(String value) {
        String normalized = normalize(value);
        return normalized == null ? "" : normalized.toUpperCase(Locale.ROOT);
    }

    private static String requireText(String value, String message) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new IllegalArgumentException(message);
        }
        return normalized;
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
