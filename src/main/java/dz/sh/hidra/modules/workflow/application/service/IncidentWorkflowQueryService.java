/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentWorkflowQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Enforces Incident lifecycle integrity and owner-controlled evidence.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import dz.sh.hidra.modules.workflow.application.contract.incident.IncidentWorkflowContract;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import java.time.Instant;
import org.springframework.stereotype.Service;
@Service
public final class IncidentWorkflowQueryService implements IncidentWorkflowContract {
    private final WorkflowInstanceRepositoryPort instances;
    public IncidentWorkflowQueryService(WorkflowInstanceRepositoryPort instances) {this.instances=java.util.Objects.requireNonNull(instances);}
    public boolean exists(String id) {return id!=null && !id.isBlank() && instances.findById(id.trim()).isPresent();}
    public boolean closureApproved(String id,String incidentId,String actorId,Instant at) {
        // Approval attestation is admitted under HMR-090; never infer it from existence.
        return false;
    }
}
