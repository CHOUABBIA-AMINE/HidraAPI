/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsPartyReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.service
 *
 * @Description : Resolves Party existence for Assets.
 *
 */
package dz.sh.hidra.modules.party.application.service;

import dz.sh.hidra.modules.party.application.contract.assets.AssetsPartyReferenceContract;
import dz.sh.hidra.modules.party.application.port.out.PartyRepositoryPort;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public final class AssetsPartyReferenceQueryService implements AssetsPartyReferenceContract {

    private final PartyRepositoryPort repository;

    public AssetsPartyReferenceQueryService(PartyRepositoryPort repository) {
        this.repository = Objects.requireNonNull(repository, "PartyRepositoryPort must not be null.");
    }

    @Override
    public boolean exists(String partyId) {
        return partyId != null
                && !partyId.isBlank()
                && repository.findById(partyId.trim()).isPresent();
    }
}
