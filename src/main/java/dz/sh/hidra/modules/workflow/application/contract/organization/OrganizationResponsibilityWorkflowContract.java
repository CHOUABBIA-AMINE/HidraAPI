/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationResponsibilityWorkflowContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.contract.organization
 *
 * @Description : Narrow Workflow approval contract exported to Organization responsibility orchestration.
 *
 */
package dz.sh.hidra.modules.workflow.application.contract.organization;

import java.time.Instant;

/**
 * Validates completed Workflow approval evidence for one Organization responsibility change.
 */
public interface OrganizationResponsibilityWorkflowContract {

    ApprovalEvidence requireCompletedApproval(
            String workflowInstanceId,
            String operationReference
    );

    record ApprovalEvidence(
            String workflowInstanceId,
            String workflowTaskId,
            String workflowActionId,
            String decision,
            Instant completedAt
    ) { }
}
