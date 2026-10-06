/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowActorContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.workflow
 *
 * @Description : Enforces owner-validated Workflow execution and immutable evidence.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.workflow;

import java.time.Instant;
import java.util.Optional;
public interface WorkflowActorContract {
    record Actor(String id, String username, String displayName, String employeeId) {}
    Optional<Actor> eligibleActor(String actorId, Instant at);
    Optional<Actor> eligibleReference(String reference, Instant at);
    boolean permitted(String actorId, String permissionCode, String instanceId);
}
