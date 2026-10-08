/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskActorQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.risk.RiskActorContract;
import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class RiskActorQueryService implements RiskActorContract {
    private final CurrentSecurityContext security;
    private final WorkflowActorContract actors;
    public RiskActorQueryService(CurrentSecurityContext security, WorkflowActorContract actors) {
        this.security = java.util.Objects.requireNonNull(security);
        this.actors = java.util.Objects.requireNonNull(actors);
    }
    public Actor currentActor(Instant at) {
        String id = security.currentPrincipal().filter(p -> p.authenticated())
                .map(p -> p.actorId().value()).orElseThrow(() -> new SecurityException("Authenticated Risk actor required."));
        var actor = actors.eligibleActor(id, at).filter(a -> id.equals(a.id()))
                .orElseThrow(() -> new SecurityException("Eligible Identity Risk actor required."));
        return new Actor(actor.id(), actor.username(), actor.displayName());
    }
}
