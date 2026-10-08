/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceWorkOrderWorkflowReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.contract.assets
 *
 * @Description : Validates owner-controlled MaintenanceWorkOrder references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.workflow.application.contract.assets;

/** Attests context; this does not attest business approval or grant permissions. */
public interface MaintenanceWorkOrderWorkflowReferenceContract { boolean matches(String instanceId, String targetId); }
