/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationProductCandidateQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.integration
 *
 * @Description : Reads existing catalogue evidence without adopting Planning approval.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.integration;

import dz.sh.hidra.modules.custody.application.contract.simulation.SimulationProductCandidateContract;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyCatalogEntryJpaRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SimulationProductCandidateQueryAdapter implements SimulationProductCandidateContract {
    private final CustodyCatalogEntryJpaRepository catalog;

    public SimulationProductCandidateQueryAdapter(CustodyCatalogEntryJpaRepository catalog) {
        this.catalog = Objects.requireNonNull(catalog, "Custody catalogue repository must not be null.");
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Candidate> resolve(String id) {
        if (id == null || id.isBlank()) return Optional.empty();
        String key = id.trim();
        return catalog.findById(key).filter(entry -> key.equals(entry.id()))
                .map(entry -> new Candidate(entry.id(), entry.catalogName(), entry.code(),
                        entry.active(), entry.createdAt(), entry.updatedAt()));
    }
}
