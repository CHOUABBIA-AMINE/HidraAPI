/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCaseActorReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Validates IntegrityCase provenance through explicit owner-controlled references.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.integrity.IntegrityCaseActorReferenceContract;
import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Service;
@Service
public final class IntegrityCaseActorReferenceQueryService implements IntegrityCaseActorReferenceContract {
    private final WorkflowActorContract actors;
    public IntegrityCaseActorReferenceQueryService(WorkflowActorContract actors) { this.actors=Objects.requireNonNull(actors); }
    @Override public boolean eligible(String id, Instant at) {
        return id!=null && !id.isBlank() && at!=null
                && actors.eligibleActor(id.trim(),at).filter(a -> id.trim().equals(a.id())).isPresent();
    }
}
