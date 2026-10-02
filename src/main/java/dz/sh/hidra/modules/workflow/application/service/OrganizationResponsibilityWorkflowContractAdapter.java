/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationResponsibilityWorkflowContractAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Adapts Workflow query state to Organization responsibility approval evidence.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import dz.sh.hidra.modules.workflow.application.contract.organization.OrganizationResponsibilityWorkflowContract;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.springframework.stereotype.Service;

/**
 * Requires a completed Workflow instance deliberately targeted to an Organization
 * responsibility-change operation reference.
 */
@Service
public final class OrganizationResponsibilityWorkflowContractAdapter
        implements OrganizationResponsibilityWorkflowContract {

    private static final String ORGANIZATION_MODULE = "organization";
    private static final String RESPONSIBILITY_CHANGE = "RESPONSIBILITY_CHANGE";
    private static final String COMPLETED = "COMPLETED";

    private final WorkflowQueryUseCase workflowQuery;

    public OrganizationResponsibilityWorkflowContractAdapter(WorkflowQueryUseCase workflowQuery) {
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
                "Responsibility change requires a workflow instance."
        );
        String reference = requireText(
                operationReference,
                "Responsibility operation reference must not be blank."
        );

        WorkflowQueryUseCase.InstanceView instance = workflowQuery.instance(instanceId);
        if (!ORGANIZATION_MODULE.equalsIgnoreCase(normalize(instance.targetModule()))) {
            throw new IllegalStateException(
                    "Workflow instance does not target the organization module: " + instance.id()
            );
        }
        if (!RESPONSIBILITY_CHANGE.equals(normalizeState(instance.targetTypeId()))) {
            throw new IllegalStateException(
                    "Workflow instance is not a responsibility-change approval: " + instance.id()
            );
        }
        if (!Objects.equals(reference, normalize(instance.targetId()))) {
            throw new IllegalStateException(
                    "Workflow instance target does not match the responsibility operation reference."
            );
        }
        if (!COMPLETED.equals(normalizeState(instance.status()))) {
            throw new IllegalStateException(
                    "Responsibility workflow approval is not completed: " + instance.id()
            );
        }

        List<WorkflowQueryUseCase.TimelineEntry> timeline = workflowQuery.timeline(instance.id());
        WorkflowQueryUseCase.TimelineEntry finalAction = timeline.stream()
                .max(Comparator.comparingLong(WorkflowQueryUseCase.TimelineEntry::sequence))
                .orElseThrow(() -> new IllegalStateException(
                        "Completed responsibility workflow has no action evidence: " + instance.id()
                ));

        String decision = requireText(
                finalAction.decision(),
                "Completed responsibility workflow has no decision evidence."
        );
        if (!"APPROVE".equals(normalizeState(decision))) {
            throw new IllegalStateException(
                    "Responsibility workflow completed without approval: " + instance.id()
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
