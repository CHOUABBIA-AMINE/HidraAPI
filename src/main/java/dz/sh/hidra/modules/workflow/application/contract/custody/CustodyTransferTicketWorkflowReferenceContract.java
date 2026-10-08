/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTransferTicketWorkflowReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.contract.custody
 *
 * @Description : Validates owner-controlled CustodyTransferTicket references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.workflow.application.contract.custody;

/** Attests context; this does not attest business approval or grant permissions. */
public interface CustodyTransferTicketWorkflowReferenceContract { boolean matches(String instanceId, String targetId); }
