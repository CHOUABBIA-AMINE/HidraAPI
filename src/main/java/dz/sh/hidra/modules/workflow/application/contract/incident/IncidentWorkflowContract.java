/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentWorkflowContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.contract.incident
 *
 * @Description : Enforces Incident lifecycle integrity and owner-controlled evidence.
 *
 */
package dz.sh.hidra.modules.workflow.application.contract.incident;

import java.time.Instant;
public interface IncidentWorkflowContract {
    boolean exists(String id);
    /** Exact completed closure approval and eligible closer; existence alone never authorizes closure. */
    boolean closureApproved(String instanceId,String incidentId,String actorId,Instant at);
}
