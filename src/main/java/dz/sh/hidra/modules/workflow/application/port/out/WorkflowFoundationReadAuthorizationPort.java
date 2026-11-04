/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowFoundationReadAuthorizationPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.out
 *
 * @Description : Requires current Workflow actor membership before returning task, instance or history evidence.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.out;

import java.util.List;

/** Business authorization uses scalar snapshots, never cross-module persistence models. */
public interface WorkflowFoundationReadAuthorizationPort {
    record Scope(String instanceId, String startedByActorId,
                 String targetModule, String targetTypeId, String targetId,
                 String targetCodeSnapshot, boolean ownerReadAdmitted) {}
    record Assignment(String assignedActorId, String claimedByActorId,
                      String completedByActorId, String assignedOrganizationUnitId) {}
    boolean permitted(Scope scope, List<Assignment> assignments);
}
