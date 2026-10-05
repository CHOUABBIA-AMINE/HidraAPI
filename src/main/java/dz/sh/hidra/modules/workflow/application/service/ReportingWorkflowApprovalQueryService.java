/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingWorkflowApprovalQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Confirms completed approval evidence for Reporting requests.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import dz.sh.hidra.modules.workflow.application.contract.reporting.ReportingWorkflowApprovalContract;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public final class ReportingWorkflowApprovalQueryService
        implements ReportingWorkflowApprovalContract {

    private final WorkflowQueryUseCase workflowQuery;

    public ReportingWorkflowApprovalQueryService(WorkflowQueryUseCase workflowQuery) {
        this.workflowQuery = Objects.requireNonNull(workflowQuery, "WorkflowQueryUseCase must not be null.");
    }

    @Override
    public boolean approved(String workflowReferenceId, String reportRequestId) {
        if (workflowReferenceId == null || workflowReferenceId.isBlank()
                || reportRequestId == null || reportRequestId.isBlank()) {
            return false;
        }

        WorkflowQueryUseCase.InstanceView instance;
        try {
            instance = workflowQuery.instance(workflowReferenceId.trim());
        } catch (RuntimeException ex) {
            return false;
        }

        if (!"COMPLETED".equals(instance.status())
                || !"reporting".equalsIgnoreCase(instance.targetModule())
                || !reportRequestId.trim().equals(instance.targetId())) {
            return false;
        }

        return workflowQuery.timeline(instance.id()).stream()
                .anyMatch(entry -> "APPROVE".equals(entry.decision()));
    }
}
