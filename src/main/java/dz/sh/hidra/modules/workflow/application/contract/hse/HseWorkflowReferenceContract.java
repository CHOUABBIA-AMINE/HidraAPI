/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseWorkflowReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.contract.hse
 *
 * @Description : Enforces HSE-owned lifecycle integrity through owner-controlled references.
 *
 */
package dz.sh.hidra.modules.workflow.application.contract.hse;

/** Context reference attestation only; does not grant approval or start a workflow. */
public interface HseWorkflowReferenceContract {
    boolean caseMatches(String instanceId, String caseId);
}
