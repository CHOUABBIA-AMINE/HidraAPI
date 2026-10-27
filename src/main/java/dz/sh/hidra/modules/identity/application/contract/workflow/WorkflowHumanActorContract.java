/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowHumanActorContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.workflow
 *
 * @Description : Provides bounded product source publication governance.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.workflow;

import java.time.Instant;
import java.util.Optional;
/** Ordinary Workflow transition check, never a protected publication admission. */
public interface WorkflowHumanActorContract {
    record Human(String id,String displayName) {}
    Optional<Human> eligibleHuman(String actorId,Instant at);
}
