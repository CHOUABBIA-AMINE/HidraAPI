/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningCreatorQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Enforces Planning-owned semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.identity.application.service;
import dz.sh.hidra.modules.identity.application.contract.planning.PlanningCreatorContract;
import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public final class PlanningCreatorQueryService implements PlanningCreatorContract {
    private final WorkflowActorContract actors;
    public PlanningCreatorQueryService(WorkflowActorContract actors) {this.actors=Objects.requireNonNull(actors);}
    @Override public Optional<Creator> eligibleCreator(String id, Instant at) {
        return actors.eligibleActor(id,at).map(actor -> new Creator(actor.id()));
    }
}
