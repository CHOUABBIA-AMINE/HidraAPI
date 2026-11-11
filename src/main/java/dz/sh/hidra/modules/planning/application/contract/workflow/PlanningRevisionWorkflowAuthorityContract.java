/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningRevisionWorkflowAuthorityContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
 *
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.contract.workflow
 *
 * @Description : Exports exact current Planning reviewer authority to Workflow transitions.
 *
 */
package dz.sh.hidra.modules.planning.application.contract.workflow;

public interface PlanningRevisionWorkflowAuthorityContract {
    /**
     * Refreshes current Identity/company/asset authority and the submitted revision binding.
     * Implementations must fail closed without disclosing the protected revision identity.
     */
    void requireReview(String revisionId, String workflowInstanceId, String starterActorId,
            String reviewerActorId);
}
