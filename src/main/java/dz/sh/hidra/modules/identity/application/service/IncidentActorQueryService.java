/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentActorQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Enforces Incident lifecycle integrity and owner-controlled evidence.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.incident.IncidentActorContract;
import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Service;
@Service
public final class IncidentActorQueryService implements IncidentActorContract {
    private final CurrentSecurityContext security;
    private final WorkflowActorContract actors;
    public IncidentActorQueryService(CurrentSecurityContext security, WorkflowActorContract actors) {
        this.security=java.util.Objects.requireNonNull(security); this.actors=java.util.Objects.requireNonNull(actors);
    }
    public Actor currentActor(Instant at) {
        String id=security.currentPrincipal().filter(p -> p.authenticated()).map(p -> p.actorId().value())
                .orElseThrow(() -> new SecurityException("Authenticated Incident actor required."));
        return eligibleActor(id,at).orElseThrow(() -> new SecurityException("Eligible Incident actor required."));
    }
    public Optional<Actor> eligibleActor(String id, Instant at) {
        if(id==null || id.isBlank()) return Optional.empty();
        return actors.eligibleActor(id.trim(),at).filter(a -> id.trim().equals(a.id()))
                .map(a -> new Actor(a.id(),a.displayName()));
    }
}
