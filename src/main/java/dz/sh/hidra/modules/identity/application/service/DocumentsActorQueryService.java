/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsActorQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Enforces Documents semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.identity.application.service;
import dz.sh.hidra.modules.identity.application.contract.documents.DocumentsActorContract;
import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import java.time.Instant;
import java.util.Optional;
import java.util.Objects;
import org.springframework.stereotype.Service;
@Service
public final class DocumentsActorQueryService implements DocumentsActorContract {
    private final WorkflowActorContract actors;
    public DocumentsActorQueryService(WorkflowActorContract actors){this.actors=Objects.requireNonNull(actors);}
    public Optional<Actor> eligibleActor(String id,Instant at){return actors.eligibleActor(id,at).map(a->new Actor(a.id(),a.displayName()));}
}
