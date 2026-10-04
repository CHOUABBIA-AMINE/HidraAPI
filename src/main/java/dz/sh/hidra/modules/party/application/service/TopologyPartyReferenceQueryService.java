/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyPartyReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.service
 *
 * @Description : Party-owned resolver for Topology Party references.
 *
 */
package dz.sh.hidra.modules.party.application.service;

import dz.sh.hidra.modules.party.application.contract.topology.TopologyPartyReferenceContract;
import dz.sh.hidra.modules.party.application.port.out.PartyRepositoryPort;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public final class TopologyPartyReferenceQueryService implements TopologyPartyReferenceContract {

    private final PartyRepositoryPort partyRepositoryPort;

    public TopologyPartyReferenceQueryService(PartyRepositoryPort partyRepositoryPort) {
        this.partyRepositoryPort = Objects.requireNonNull(
                partyRepositoryPort,
                "PartyRepositoryPort must not be null."
        );
    }

    @Override
    public boolean exists(String partyId) {
        if (partyId == null || partyId.isBlank()) {
            return false;
        }
        return partyRepositoryPort.findById(partyId.trim()).isPresent();
    }
}
