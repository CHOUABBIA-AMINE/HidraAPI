/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionWorkflowContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.contract.alarm
 *
 * @Description : Narrow Workflow approval contract exported to Alarm suppression governance.
 *
 */
package dz.sh.hidra.modules.workflow.application.contract.alarm;

import java.time.Instant;

/**
 * Validates completed Workflow approval evidence for one open-ended alarm suppression operation.
 */
public interface AlarmSuppressionWorkflowContract {

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
