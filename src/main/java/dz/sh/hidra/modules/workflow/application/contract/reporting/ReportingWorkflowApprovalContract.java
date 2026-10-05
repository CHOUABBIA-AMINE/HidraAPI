/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingWorkflowApprovalContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.contract.reporting
 *
 * @Description : Workflow-owned approval evidence contract exported to Reporting.
 *
 */
package dz.sh.hidra.modules.workflow.application.contract.reporting;

public interface ReportingWorkflowApprovalContract {

    boolean approved(String workflowReferenceId, String reportRequestId);
}
