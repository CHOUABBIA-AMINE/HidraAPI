/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopExternalPartyMasterDataClient
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.integration
 *
 * @Description : No-op external party master-data client.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.integration;

import java.util.Map;
import java.util.Optional;

/**
 * No-op external party master-data client.
 */
public class NoopExternalPartyMasterDataClient implements ExternalPartyMasterDataClient {

    @Override
    public Optional<Map<String, String>> findExternalParty(String externalSystemCode, String externalReference) {
        return Optional.empty();
    }
}
