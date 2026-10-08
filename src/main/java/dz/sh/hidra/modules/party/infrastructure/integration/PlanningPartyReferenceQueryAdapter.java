/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningPartyReferenceQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.integration
 *
 * @Description : Enforces owner-controlled Nomination reference integrity.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.integration;

import dz.sh.hidra.modules.party.application.contract.planning.PlanningPartyReferenceContract;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyJpaRepository;
import java.util.Optional;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

@Component("planningPartyReferenceQueryAdapter")
public class PlanningPartyReferenceQueryAdapter implements PlanningPartyReferenceContract {
    private final PartyJpaRepository repository;
    public PlanningPartyReferenceQueryAdapter(PartyJpaRepository repository) {this.repository=Objects.requireNonNull(repository);}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Party> resolve(String id) {
        if(id==null || id.isBlank()) return Optional.empty();
        return repository.findByIdForShare(id).filter(p->id.equals(p.id())).map(p->new Party(p.id(),p.code()));
    }
}
