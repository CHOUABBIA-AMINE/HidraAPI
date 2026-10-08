/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityAssessmentWorkflowReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.contract.integrity
 *
 * @Description : Validates owner-controlled IntegrityAssessment references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.workflow.application.contract.integrity;

/** Attests context; this does not attest business approval or grant permissions. */
public interface IntegrityAssessmentWorkflowReferenceContract { boolean matches(String instanceId, String targetId); }
