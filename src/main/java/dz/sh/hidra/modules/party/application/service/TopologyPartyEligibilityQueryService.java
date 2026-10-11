/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyPartyEligibilityQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.service
 *
 * @Description : Resolves exact Party eligibility without granting Topology access.
 *
 */
package dz.sh.hidra.modules.party.application.service;

import dz.sh.hidra.modules.party.application.contract.topology.TopologyPartyEligibilityContract;
import dz.sh.hidra.modules.party.application.port.out.PartyRepositoryPort;
import java.util.Objects;
import org.springframework.stereotype.Service;

/** Stateless Party eligibility resolver; each lookup uses current owner data. */
@Service
public final class TopologyPartyEligibilityQueryService implements TopologyPartyEligibilityContract {
    private final PartyRepositoryPort repository;

    public TopologyPartyEligibilityQueryService(PartyRepositoryPort repository) {
        this.repository = Objects.requireNonNull(repository, "PartyRepositoryPort must not be null.");
    }

    @Override
    public boolean eligibleForNewReference(String partyId) {
        if (partyId == null || partyId.isBlank()) {
            return false;
        }
        String identity = partyId.trim();
        return repository.findById(identity)
                .filter(party -> identity.equals(party.id()))
                .map(party -> party.selectableForNewReference())
                .orElse(false);
    }
}
