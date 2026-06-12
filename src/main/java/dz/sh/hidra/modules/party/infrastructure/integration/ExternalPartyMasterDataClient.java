/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalPartyMasterDataClient
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.integration
 *
 * @Description : External party master-data client contract.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.integration;

import java.util.Map;
import java.util.Optional;

/**
 * External party master-data client contract.
 */
public interface ExternalPartyMasterDataClient {

    Optional<Map<String, String>> findExternalParty(String externalSystemCode, String externalReference);
}
