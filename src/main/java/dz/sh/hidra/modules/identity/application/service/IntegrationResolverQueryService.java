/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationResolverQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Enforces Integration evidence integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.identity.application.service;
import dz.sh.hidra.modules.identity.application.contract.integration.IntegrationResolverContract;
import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Service;
@Service
public final class IntegrationResolverQueryService implements IntegrationResolverContract {
    private final WorkflowActorContract actors;
    public IntegrationResolverQueryService(WorkflowActorContract actors){this.actors=Objects.requireNonNull(actors);}
    public boolean eligibleResolver(String actorId,Instant at){
        return actorId!=null && !actorId.isBlank() && at!=null && actors.eligibleActor(actorId.trim(),at).isPresent();
    }
}
